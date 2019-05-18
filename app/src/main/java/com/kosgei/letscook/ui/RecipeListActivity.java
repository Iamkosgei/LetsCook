package com.kosgei.letscook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.kosgei.letscook.R;
import com.kosgei.letscook.services.EdamamService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeListActivity extends AppCompatActivity {
    String category = "chicken";
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

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
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
