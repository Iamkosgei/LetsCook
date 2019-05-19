package com.kosgei.letscook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.RecipeListAdapter;
import com.kosgei.letscook.models.Recipe;
import com.kosgei.letscook.services.EdamamService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";
    @BindView(R.id.recipeListRecycler)
    RecyclerView recyclerView;

    RecipeListAdapter recipeListAdapter;

    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        //get category from home activity
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        getRecipes(category);

    }

    private void getRecipes(String category)
    {

        final EdamamService edamamService = new EdamamService();
        edamamService.findRecipes(category, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                recipes = edamamService.processResults(response);

                RecipeListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recipeListAdapter = new RecipeListAdapter(getApplicationContext(),recipes);
                        recyclerView.setAdapter(recipeListAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);

                    }
                });

            }
        });
    }
}
