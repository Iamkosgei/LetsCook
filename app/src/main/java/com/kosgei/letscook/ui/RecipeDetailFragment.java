package com.kosgei.letscook.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Recipe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recipe_image)
    ImageView image;
    @BindView(R.id.recipe_name)
    TextView name;
    @BindView(R.id.ingredients_list)
    TextView ingredients;
    @BindView(R.id.ingredients_listHealthLabels)
    TextView healthLabels;
    @BindView(R.id.ingredients_listDietLabels)
    TextView dietLabels;
    @BindView(R.id.ingredients_listCautions)
    TextView cautions;
    @BindView(R.id.recipe_source)
    TextView source;
    @BindView(R.id.total_time)
    TextView time;
    @BindView(R.id.total_weight)
    TextView weight;
    @BindView(R.id.open_in_browser)
    Button openButton;

    private Recipe recipe;

    public static RecipeDetailFragment newInstances(Recipe recipe) {
      RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipes", Parcels.wrap(recipe));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = Parcels.unwrap(getArguments().getParcelable("recipes"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        openButton.setOnClickListener(this);

        Picasso.get().load(recipe.getImage()).into(image);
        name.setText(recipe.getName());
        ingredients.setText(android.text.TextUtils.join("\n ",recipe.getIngredients()));
        healthLabels.setText(android.text.TextUtils.join("\n ",recipe.getHealthLabels()));
        dietLabels.setText(android.text.TextUtils.join("\n ",recipe.getDietLabels()));
        cautions.setText(android.text.TextUtils.join("\n ",recipe.getCautions()));
        source.setText(recipe.getSource());
        time.setText(recipe.getTotalTime());
        weight.setText(recipe.getTotalWeight());
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == openButton)
        {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getUlr()));
            startActivity(webIntent);
        }
    }
}
