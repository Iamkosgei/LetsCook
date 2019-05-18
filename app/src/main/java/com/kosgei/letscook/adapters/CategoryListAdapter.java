package com.kosgei.letscook.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Category;
import com.kosgei.letscook.ui.RecipeListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {
    private ArrayList<Category> categories = new ArrayList<>();
    private Context context;

    private static final String TAG = "CategoryListAdapter";
    public CategoryListAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bindCategory(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.category_name)
        TextView categoryTV;
        @BindView(R.id.category_image)
        ImageView categoryImageView;

        private Context context;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindCategory(Category category)
        {
         categoryTV.setText(category.getName());
         Picasso.get().load(category.getUrl()).into(categoryImageView);

        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            context.startActivity(new Intent(context, RecipeListActivity.class));
        }
    }
}
