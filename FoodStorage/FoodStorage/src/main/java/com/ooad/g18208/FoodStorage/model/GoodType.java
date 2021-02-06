package com.ooad.g18208.FoodStorage.model;

public class GoodType {
    private int typeId;
    private String category;
    private String subcategory;
    private boolean vitality;

    public GoodType(String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public GoodType(String category, String subcategory, boolean vitality) {
        this.category = category;
        this.subcategory = subcategory;
        this.vitality = vitality;
    }

    public GoodType() {

    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public boolean getVitality() {
        return vitality;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setVitality(boolean vitality) {
        this.vitality = vitality;
    }

}
