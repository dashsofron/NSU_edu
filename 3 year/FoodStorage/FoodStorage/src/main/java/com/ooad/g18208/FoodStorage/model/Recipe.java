package com.ooad.g18208.FoodStorage.model;

import java.util.List;

public class Recipe {
    private String name;
    private int id;
    private long time;
    private int difficulty;
    private String rule;
    private List<NeededGood> ingredients;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getRule() {
        return rule;
    }

    public List<NeededGood> getIngredients() {
        return ingredients;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setIngredients(List<NeededGood> ingredients) {
        this.ingredients = ingredients;
    }
}
