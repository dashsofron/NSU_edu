package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.NeededGood;

import java.util.Collection;

public interface IWishListGoodRepository {
    NeededGood getWishListGood(String goodId);

    Collection<NeededGood> getWishListGoods();

    void deleteWishListGood(String goodId);

    NeededGood updateWishListGood(String goodId, String listId, NeededGood good);

    NeededGood createWishListGood(NeededGood good, String listId);
}
