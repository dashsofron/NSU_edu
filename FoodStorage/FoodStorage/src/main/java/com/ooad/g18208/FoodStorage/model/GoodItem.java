package com.ooad.g18208.FoodStorage.model;

public class GoodItem extends GoodType {
    private int id;
    private int daysBeforeSpoiled;
    private String name;
    private String producer;
    private float protein;
    private float fat;
    private float carbohydrate;
    private int calories;

    public GoodItem(int id, int daysBeforeSpoiled, String name, String producer, float protein, float fat, float carbohydrate, int calories) {
        this.id = id;

        this.daysBeforeSpoiled = daysBeforeSpoiled;
        this.name = name;
        this.producer = producer;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calories = calories;
    }

    public GoodItem(int id, int daysBeforeSpoiled, String name, String producer, float protein, float fat, float carbohydrate, int calories, String category, String subcategory) {
        this.id = id;
        this.daysBeforeSpoiled = daysBeforeSpoiled;
        this.name = name;
        this.producer = producer;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calories = calories;
        this.setCategory(category);
        this.setSubcategory(subcategory);
    }

    public GoodItem() {

    }


    public void setGoodType(GoodType goodType) {
        this.setCategory(goodType.getCategory());
        this.setSubcategory(goodType.getSubcategory());
        this.setVitality(goodType.getVitality());
        this.setTypeId(goodType.getTypeId());
    }

    public int getId() {
        return id;
    }

    public int getDaysBeforeSpoiled() {
        return daysBeforeSpoiled;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public float getProtein() {
        return protein;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public int getCalories() {
        return calories;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDaysBeforeSpoiled(int daysBeforeSpoiled) {
        this.daysBeforeSpoiled = daysBeforeSpoiled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }


}
