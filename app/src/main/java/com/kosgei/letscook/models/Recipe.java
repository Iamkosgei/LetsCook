package com.kosgei.letscook.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String image;
    private String source;
    private String ulr;
    private ArrayList<String> dietLabels = new ArrayList<>();
    private ArrayList<String> healthLabels = new ArrayList<>();
    private ArrayList<String> cautions = new ArrayList<>();
    private ArrayList<String> ingredients = new ArrayList<>();
    private String calories;
    private String totalWeight;
    private String totalTime;

    public Recipe() {
    }

    public Recipe(String name, String image, String source, String ulr, ArrayList<String> dietLabels, ArrayList<String> healthLabels, ArrayList<String> cautions, ArrayList<String> ingredients, String calories, String totalWeight, String totalTime) {
        this.name = name;
        this.image = image;
        this.source = source;
        this.ulr = ulr;
        this.dietLabels = dietLabels;
        this.healthLabels = healthLabels;
        this.cautions = cautions;
        this.ingredients = ingredients;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public ArrayList<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(ArrayList<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public ArrayList<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(ArrayList<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public ArrayList<String> getCautions() {
        return cautions;
    }

    public void setCautions(ArrayList<String> cautions) {
        this.cautions = cautions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
