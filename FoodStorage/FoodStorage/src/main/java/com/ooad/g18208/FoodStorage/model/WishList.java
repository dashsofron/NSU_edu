package com.ooad.g18208.FoodStorage.model;

import java.util.List;

public class WishList {
    private int id;
    private List<NeededGood> good;
    private String name;

    public WishList() {
    }

    public WishList(int id, String name) {
        this.name = name;
        this.id = id;

    }

    public WishList(int id, String name, List<NeededGood> good) {
        this.id = id;
        this.name = name;
        this.good = good;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public NeededGood getNeededGood(int i) {
        return good.get(i);
    }

    public List<NeededGood> getNeededGoods() {
        return good;
    }


    public void setGood(List<NeededGood> good) {
        this.good = good;
    }

    public void addGood(NeededGood good) {
        this.good.add(good);
    }

    public void setName(String name) {
        this.name = name;
    }
}
