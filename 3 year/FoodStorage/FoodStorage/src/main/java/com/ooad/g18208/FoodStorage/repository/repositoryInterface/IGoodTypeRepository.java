package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.GoodType;

import java.util.Collection;

public interface IGoodTypeRepository {
    GoodType getGoodType(String goodTypeId);

    Collection<GoodType> getGoodTypes();

    void deleteGoodType(String goodTypeId);

    GoodType updateGoodType(String goodTypeId, GoodType good);

    GoodType createGoodType(GoodType good);
}
