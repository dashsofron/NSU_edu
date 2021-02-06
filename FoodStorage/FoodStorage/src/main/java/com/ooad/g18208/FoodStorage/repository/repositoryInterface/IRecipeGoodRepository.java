package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.NeededGood;

import java.util.Collection;

public interface IRecipeGoodRepository {
    NeededGood getRecipeGood(String goodId);

    Collection<NeededGood> getRecipeGoods();

    void deleteRecipeGood(String goodId);

    NeededGood updateRecipeGood(String goodId, String recipeId, NeededGood good);

    NeededGood createRecipeGood(NeededGood good, String listId);
}
