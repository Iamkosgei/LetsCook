package com.kosgei.letscook.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Recipe;
import com.kosgei.letscook.ui.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRecipeViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;
    FirebaseUser user;

    public FirebaseRecipeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void bindRestaurant(Recipe recipe) {
        TextView name = mView.findViewById(R.id.recipe_name);
        ImageView image = mView.findViewById(R.id.recipe_image);

        Picasso.get().load(recipe.getImage()).into(image);
        name.setText(recipe.getName());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Recipe> recipes = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(user.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recipes.add(snapshot.getValue(Recipe.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("recipes", Parcels.wrap(recipes));

                Log.d("heyy", String.valueOf(recipes.get(0).getName()));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
