package com.kosgei.letscook.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Meal {
    private String id,meal,name,category,area,instructions,tags,youtube,source,image;
    private List<String> ingredients;
    private List<String> measure;
    private String mealUid;
    String index;

    public Meal() {
    }

    public Meal(String id,String name, String category, String area, String instructions, String tags, String youtube, String source, String image, List<String> ingredients, List<String> measure) {
        this.id = id;
        this.meal = meal;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.tags = tags;
        this.youtube = youtube;
        this.source = source;
        this.image = image;
        this.ingredients = ingredients;
        this.measure = measure;

        this.index = "not_specified";
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasure() {
        return measure;
    }

    public void setMeasure(List<String> measure) {
        this.measure = measure;
    }

    public String getMealUid() {
        return mealUid;
    }

    public void setMealUid(String mealUid) {
        this.mealUid = mealUid;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
