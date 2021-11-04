package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.GoodItem;

import java.util.Collection;

public interface IGoodRepository {
    GoodItem getGood(String goodId);

    Collection<GoodItem> getGoods();

    void deleteGood(String goodId);

    GoodItem updateGood(String goodId, GoodItem good);

    GoodItem createGood(GoodItem good);
}
