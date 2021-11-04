package com.ooad.g18208.FoodStorage.model;

public class NeededGood extends GoodType {
    private int id;
    private int amount;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setGoodType(GoodType goodType) {
        this.setCategory(goodType.getCategory());
        this.setSubcategory(goodType.getSubcategory());
        this.setVitality(goodType.getVitality());
        this.setTypeId(goodType.getTypeId());
    }

    public int getAmount() {
        return amount;
    }
}
