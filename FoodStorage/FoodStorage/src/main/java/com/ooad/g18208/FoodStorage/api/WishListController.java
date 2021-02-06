package com.ooad.g18208.FoodStorage.api;

import com.ooad.g18208.FoodStorage.model.WishList;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IWishListRepository;
import com.ooad.g18208.FoodStorage.exception.CreateException;
import com.ooad.g18208.FoodStorage.exception.LackOfIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController

public class WishListController {
    private final String get_ = "/get";
    private final String delete_ = "/delete";
    private final String update_ = "/update";
    private final String create_ = "/create";
    private final String getAll_ = "/get/all";
    private final String wishList_ = "/api/wishList";
    IWishListRepository rep;

    @Autowired
    WishListController(IWishListRepository rep) {
        this.rep = rep;
    }

    @GetMapping(wishList_ + get_ + "/{wishListId}")
    public ResponseEntity<WishList> getWishList(
            @PathVariable String wishListId) {
        WishList wishList = rep.getWishList(wishListId);
        if(wishList==null) throw new LackOfIdException();

        return ResponseEntity.ok(wishList);
    }

    @GetMapping(wishList_ + getAll_)
    public ResponseEntity<Collection<WishList>> getWishLists() {
        Collection<WishList> wishLists = rep.getWishLists();
        if(wishLists==null) throw new LackOfIdException();

        return ResponseEntity.ok(wishLists);
    }

    @PatchMapping(wishList_ + update_ + "/{wishListId}")
    public ResponseEntity<WishList> updateWishList(
            @PathVariable String wishListId,
            @RequestBody WishList wishList
    ) {
        WishList updatedWishList = rep.updateWishList(wishListId, wishList);
        if(updatedWishList==null) throw new LackOfIdException();

        return ResponseEntity.ok(updatedWishList);
    }

    @PostMapping(wishList_ + create_)
    public ResponseEntity<WishList> addWishList(
            @RequestBody WishList wishList) {
        WishList result = rep.createWishList(wishList);
        if(result==null) throw new CreateException();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(wishList_ + delete_ + "/{wishListId}")
    public ResponseEntity<?> deleteWishList(
            @PathVariable String wishListId) {
        rep.deleteWishList(wishListId);
        return ResponseEntity.ok().build();
    }
}
