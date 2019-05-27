package com.kosgei.letscook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.models.Recipe;
import com.kosgei.letscook.ui.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LatestMealsAdapter extends RecyclerView.Adapter<LatestMealsAdapter.MealViewHolder> {
    private Context context;
    private ArrayList<Meal> meals;

    public LatestMealsAdapter(Context context,ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public LatestMealsAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,parent,false);
        return new LatestMealsAdapter.MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestMealsAdapter.MealViewHolder  holder, int position) {
        holder.bindRecipe(meals.get(position));
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)
        TextView recipeTV;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;

        private Context context;

        MealViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRecipe(Meal recipe)
        {
            recipeTV.setText(recipe.getName());
            Picasso.get().load(recipe.getImage()).into(recipeImageView);
        }



        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipes", Parcels.wrap(meals));
            context.startActivity(intent);

        }
    }
}

