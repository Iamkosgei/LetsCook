package com.kosgei.letscook.services;

import com.kosgei.letscook.Constants;
import com.kosgei.letscook.models.Category;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EdamamService {
    ArrayList<Meal> meals = new ArrayList<>();

    //find by category
    public static void findRecipes(String category, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.THE_MEAL_DB_FILTER_BY_CATEGORY).newBuilder();
        urlBuilder.addQueryParameter("c",category);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    public ArrayList<String> processFindRecipeResults(Response response) {
        ArrayList<String> mealsIds = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject latestMealsJson = new JSONObject(jsonData);
            JSONArray mealsJson = latestMealsJson.getJSONArray("meals");
            if (response.isSuccessful()) {
                for (int i = 0; i < mealsJson.length(); i++) {
                    JSONObject recipeJSON = mealsJson.getJSONObject(i);
                    mealsIds.add(recipeJSON.getString("idMeal"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mealsIds;
    }




    //latest meals
    public static void getLatestMeals(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.THE_MEAL_DB_LATEST_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Meal> processLatestMealsResults(Response response) {
        ArrayList<Meal> latestMeals = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject latestMealsJson = new JSONObject(jsonData);
            JSONArray mealsJson = latestMealsJson.getJSONArray("meals");

            if (response.isSuccessful()) {
                for (int i = 0; i < mealsJson.length(); i++) {
                    JSONObject recipeJSON = mealsJson.getJSONObject(i);

                    String id = recipeJSON.getString("idMeal");
                    String name = recipeJSON.getString("strMeal");
                    String category = recipeJSON.getString("strCategory");
                    String areas = recipeJSON.getString("strArea");
                    String instructions = recipeJSON.getString("strInstructions");
                    String tags = recipeJSON.getString("strTags");
                    String youtube = recipeJSON.getString("strYoutube");
                    String source = recipeJSON.getString("strSource");
                    String image = recipeJSON.getString("strMealThumb");


                    List<String> ingredients = new ArrayList<>();
                    ingredients.add(recipeJSON.getString("strIngredient1"));
                    ingredients.add(recipeJSON.getString("strIngredient2"));
                    ingredients.add(recipeJSON.getString("strIngredient3"));
                    ingredients.add(recipeJSON.getString("strIngredient4"));
                    ingredients.add(recipeJSON.getString("strIngredient5"));
                    ingredients.add(recipeJSON.getString("strIngredient6"));
                    ingredients.add(recipeJSON.getString("strIngredient7"));
                    ingredients.add(recipeJSON.getString("strIngredient8"));
                    ingredients.add(recipeJSON.getString("strIngredient9"));
                    ingredients.add(recipeJSON.getString("strIngredient10"));
                    ingredients.add(recipeJSON.getString("strIngredient11"));
                    ingredients.add(recipeJSON.getString("strIngredient12"));
                    ingredients.add(recipeJSON.getString("strIngredient13"));
                    ingredients.add(recipeJSON.getString("strIngredient14"));
                    ingredients.add(recipeJSON.getString("strIngredient15"));
                    ingredients.add(recipeJSON.getString("strIngredient16"));
                    ingredients.add(recipeJSON.getString("strIngredient17"));
                    ingredients.add(recipeJSON.getString("strIngredient18"));
                    ingredients.add(recipeJSON.getString("strIngredient19"));
                    ingredients.add(recipeJSON.getString("strIngredient20"));

                    List<String> measure = new ArrayList<>();
                    measure.add(recipeJSON.getString("strMeasure1"));
                    measure.add(recipeJSON.getString("strMeasure2"));
                    measure.add(recipeJSON.getString("strMeasure3"));
                    measure.add(recipeJSON.getString("strMeasure4"));
                    measure.add(recipeJSON.getString("strMeasure5"));
                    measure.add(recipeJSON.getString("strMeasure6"));
                    measure.add(recipeJSON.getString("strMeasure7"));
                    measure.add(recipeJSON.getString("strMeasure8"));
                    measure.add(recipeJSON.getString("strMeasure9"));
                    measure.add(recipeJSON.getString("strMeasure10"));
                    measure.add(recipeJSON.getString("strMeasure11"));
                    measure.add(recipeJSON.getString("strMeasure12"));
                    measure.add(recipeJSON.getString("strMeasure13"));
                    measure.add(recipeJSON.getString("strMeasure14"));
                    measure.add(recipeJSON.getString("strMeasure15"));
                    measure.add(recipeJSON.getString("strMeasure16"));
                    measure.add(recipeJSON.getString("strMeasure17"));
                    measure.add(recipeJSON.getString("strMeasure18"));
                    measure.add(recipeJSON.getString("strMeasure19"));
                    measure.add(recipeJSON.getString("strMeasure20"));


                    Meal meal = new Meal(id, name, category, areas, instructions, tags, youtube, source, image, ingredients, measure);
                    latestMeals.add(meal);


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return latestMeals;
    }

    //get categories
    public static void getAllCategories(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.THE_MEAL_DB_ALL_CATEGORIES_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Category> processCategoryResults(Response response) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject latestMealsJson = new JSONObject(jsonData);
            JSONArray mealsJson = latestMealsJson.getJSONArray("categories");
            if (response.isSuccessful()) {
                for (int i = 0; i < mealsJson.length(); i++) {
                    JSONObject recipeJSON = mealsJson.getJSONObject(i);

                    String id = recipeJSON.getString("idCategory");
                    String name = recipeJSON.getString("strCategory");
                    String categoryDescription = recipeJSON.getString("strCategoryDescription");
                    String url = recipeJSON.getString("strCategoryThumb");

                    Category category = new Category(id,name,url,categoryDescription);
                    categories.add(category);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }



    //find meal details by id
    public static void findDetails(String id, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.THE_MEAL_DB_FIND_MEAL_BY_ID).newBuilder();
        urlBuilder.addQueryParameter("i",id);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }



    public ArrayList<Meal> processMealsResults(Response response) {
        try {
            String jsonData = response.body().string();
            JSONObject latestMealsJson = new JSONObject(jsonData);
            JSONArray mealsJson = latestMealsJson.getJSONArray("meals");

            if (response.isSuccessful()) {
                for (int i = 0; i < mealsJson.length(); i++) {
                    JSONObject recipeJSON = mealsJson.getJSONObject(i);

                    String id = recipeJSON.getString("idMeal");
                    String name = recipeJSON.getString("strMeal");
                    String category = recipeJSON.getString("strCategory");
                    String areas = recipeJSON.getString("strArea");
                    String instructions = recipeJSON.getString("strInstructions");
                    String tags = recipeJSON.getString("strTags");
                    String youtube = recipeJSON.getString("strYoutube");
                    String source = recipeJSON.getString("strSource");
                    String image = recipeJSON.getString("strMealThumb");


                    List<String> ingredients = new ArrayList<>();
                    ingredients.add(recipeJSON.getString("strIngredient1"));
                    ingredients.add(recipeJSON.getString("strIngredient2"));
                    ingredients.add(recipeJSON.getString("strIngredient3"));
                    ingredients.add(recipeJSON.getString("strIngredient4"));
                    ingredients.add(recipeJSON.getString("strIngredient5"));
                    ingredients.add(recipeJSON.getString("strIngredient6"));
                    ingredients.add(recipeJSON.getString("strIngredient7"));
                    ingredients.add(recipeJSON.getString("strIngredient8"));
                    ingredients.add(recipeJSON.getString("strIngredient9"));
                    ingredients.add(recipeJSON.getString("strIngredient10"));
                    ingredients.add(recipeJSON.getString("strIngredient11"));
                    ingredients.add(recipeJSON.getString("strIngredient12"));
                    ingredients.add(recipeJSON.getString("strIngredient13"));
                    ingredients.add(recipeJSON.getString("strIngredient14"));
                    ingredients.add(recipeJSON.getString("strIngredient15"));
                    ingredients.add(recipeJSON.getString("strIngredient16"));
                    ingredients.add(recipeJSON.getString("strIngredient17"));
                    ingredients.add(recipeJSON.getString("strIngredient18"));
                    ingredients.add(recipeJSON.getString("strIngredient19"));
                    ingredients.add(recipeJSON.getString("strIngredient20"));

                    List<String> measure = new ArrayList<>();
                    measure.add(recipeJSON.getString("strMeasure1"));
                    measure.add(recipeJSON.getString("strMeasure2"));
                    measure.add(recipeJSON.getString("strMeasure3"));
                    measure.add(recipeJSON.getString("strMeasure4"));
                    measure.add(recipeJSON.getString("strMeasure5"));
                    measure.add(recipeJSON.getString("strMeasure6"));
                    measure.add(recipeJSON.getString("strMeasure7"));
                    measure.add(recipeJSON.getString("strMeasure8"));
                    measure.add(recipeJSON.getString("strMeasure9"));
                    measure.add(recipeJSON.getString("strMeasure10"));
                    measure.add(recipeJSON.getString("strMeasure11"));
                    measure.add(recipeJSON.getString("strMeasure12"));
                    measure.add(recipeJSON.getString("strMeasure13"));
                    measure.add(recipeJSON.getString("strMeasure14"));
                    measure.add(recipeJSON.getString("strMeasure15"));
                    measure.add(recipeJSON.getString("strMeasure16"));
                    measure.add(recipeJSON.getString("strMeasure17"));
                    measure.add(recipeJSON.getString("strMeasure18"));
                    measure.add(recipeJSON.getString("strMeasure19"));
                    measure.add(recipeJSON.getString("strMeasure20"));


                    Meal meal = new Meal(id, name, category, areas, instructions, tags, youtube, source, image, ingredients, measure);
                    meals.add(meal);

                    System.out.println(name);


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return meals;
    }




}
