package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.WishList;

import java.util.Collection;

public interface IWishListRepository {
    WishList getWishList(String listId);

    Collection<WishList> getWishLists();

    void deleteWishList(String listId);

    WishList updateWishList(String listId, WishList wishList);

    WishList createWishList(WishList wishList);
}
