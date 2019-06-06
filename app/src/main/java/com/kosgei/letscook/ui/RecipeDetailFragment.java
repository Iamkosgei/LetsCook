package com.kosgei.letscook.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.models.Recipe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.category)
    Chip category;
    @BindView(R.id.area)
    Chip area;
    @BindView(R.id.ingredients)
    TextView ingredients;
    @BindView(R.id.measurements)
    TextView measurement;
    @BindView(R.id.directions)
    TextView directions;
    @BindView(R.id.video)
    Button video;
    @BindView(R.id.save)
    Button save;

    private Meal meal;

    private String mSource ="";

    private static final int REQUEST_IMAGE_CAPTURE = 111;

    private ArrayList<Meal> mMeals;
    private int mPosition;

    public static RecipeDetailFragment newInstance(ArrayList<Meal> meals, Integer position) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable("recipes", Parcels.wrap(meals));
        args.putInt("position", position);

        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }


    public static RecipeDetailFragment newInstances(Meal meal) {
      RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipes", Parcels.wrap(meal));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // meal = Parcels.unwrap(getArguments().getParcelable("recipes"));

        setHasOptionsMenu(true);

        mMeals = Parcels.unwrap(getArguments().getParcelable("recipes"));
        mPosition = getArguments().getInt("position");
        meal = mMeals.get(mPosition);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        video.setOnClickListener(this);
        save.setOnClickListener(this);

        Picasso.get().load(meal.getImage()).into(image);
        name.setText(meal.getName());
        ingredients.setText(android.text.TextUtils.join("\n ",removeEmptySpaces(meal.getIngredients())));
        measurement.setText(android.text.TextUtils.join("\n : ",removeEmptySpaces(meal.getMeasure())));
        category.setText(meal.getCategory());
        area.setText(meal.getArea());
        directions.setText(meal.getInstructions());


       return view;
    }

    private List<String> removeEmptySpaces(List<String> list)
    {
        List<String> nonEmpty= new ArrayList<>();

        for(String x:list)
        {
            if (!x.trim().isEmpty())
            {
                nonEmpty.add(x);
            }
        }
        return nonEmpty;
    }

    @Override
    public void onClick(View v) {

        if (v == video)
        {
            if (!meal.getYoutube().isEmpty())
            {

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(meal.getYoutube()));
                startActivity(webIntent);

            }
            else
                Toast.makeText(getContext(), "Sorry,No Video at the Moment", Toast.LENGTH_SHORT).show();

        }

        if(v == save)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(uid);


            DatabaseReference pushRef = recipeRef.child(meal.getId());
            meal.setMealUid("123");
            pushRef.setValue(meal);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_photo, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           image.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RECIPES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(meal.getId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }
}
