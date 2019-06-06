package com.kosgei.letscook.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.models.Recipe;
import com.kosgei.letscook.ui.RecipeDetailActivity;
import com.kosgei.letscook.ui.RecipeDetailFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private Context context;
    private ArrayList<Meal> recipes;

    public RecipeListAdapter(Context context,ArrayList<Meal> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_3,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindRecipe(recipes.get(position));
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)
        TextView recipeTV;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;

        private Context context;

        private int mOrientation;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mOrientation = itemView.getResources().getConfiguration().orientation;

            // Checks if the recorded orientation matches Android's landscape configuration.
            // if so, we create a new DetailFragment to display in our special landscape layout:
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
        }

        // Takes position of restaurant in list as parameter:
        private void createDetailFragment(int position) {
            // Creates new RestaurantDetailFragment with the given position:
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipes, position);
            // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
            FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            //  Replaces the FrameLayout with the RestaurantDetailFragment:
            ft.replace(R.id.recipeDetailContainer, detailFragment);
            // Commits these changes:
            ft.commit();
        }

       public void bindRecipe(Meal meal)
        {
            recipeTV.setText(meal.getName());
            Picasso.get().load(meal.getImage()).into(recipeImageView);
        }



        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else
            {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("recipes", Parcels.wrap(recipes));
                context.startActivity(intent);
            }
        }
    }
}
