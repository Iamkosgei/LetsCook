package com.kosgei.letscook.services;

import com.kosgei.letscook.Constants;
import com.kosgei.letscook.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EdamamService {

    public static void findRecipes(String category, Callback callback)
    {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("app_id",Constants.EDAMAM_APP_ID);
        urlBuilder.addQueryParameter("app_key" ,Constants.EDAMAM_APP_KEY);
        urlBuilder.addQueryParameter(Constants.EDAMAM_CATEGORY_QUERY_PARAMETER,category);
        urlBuilder.addQueryParameter(Constants.EDAMAM_FROM_QUERY_PARAMETER,"0");
        urlBuilder.addQueryParameter(Constants.EDAMAM_TO_QUERY_PARAMETER,"100");

        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public ArrayList<Recipe> processResults(Response response) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject edamamJSON = new JSONObject(jsonData);
            JSONArray hitsJSON = edamamJSON.getJSONArray("hits");

            if (response.isSuccessful()) {
                for (int i = 0; i < hitsJSON.length(); i++){
                    JSONObject recipeJSON = hitsJSON.getJSONObject(i).getJSONObject("recipe");

                    String name = recipeJSON.getString("label");
                    String image = recipeJSON.getString("image");
                    String source = recipeJSON.getString("source");
                    String url =recipeJSON.getString("url");

                    ArrayList<String> dietLabels = new ArrayList<>();
                    JSONArray dietLabelsJSON = recipeJSON.getJSONArray("dietLabels");
                    for (int j = 0; j < dietLabelsJSON.length(); j++){
                        dietLabels.add(dietLabelsJSON.get(j).toString());
                    }

                    ArrayList<String> healthLabels = new ArrayList<>();
                    JSONArray healthLabelsJSON = recipeJSON.getJSONArray("healthLabels");
                    for (int k = 0; k < healthLabelsJSON.length(); k++){
                        healthLabels.add(healthLabelsJSON.get(k).toString());
                    }

                    ArrayList<String> cautions = new ArrayList<>();
                    JSONArray cautionsJSON = recipeJSON.getJSONArray("cautions");
                    for (int k = 0; k < cautionsJSON.length(); k++){
                        cautions.add(cautionsJSON.get(k).toString());
                    }

                    ArrayList<String> ingredients = new ArrayList<>();
                    JSONArray ingredientsJSON = recipeJSON.getJSONArray("ingredientLines");
                    for (int k = 0; k < ingredientsJSON.length(); k++){
                        ingredients.add(ingredientsJSON.get(k).toString());
                    }

                    String calories = recipeJSON.getString("calories");
                    String totalWeight = recipeJSON.getString("totalWeight");
                    String totalTime = recipeJSON.getString("totalTime");

                    Recipe recipe = new Recipe(name,image,source,url,dietLabels,healthLabels,cautions,ingredients,calories,totalWeight,totalTime);
                    recipes.add(recipe);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
