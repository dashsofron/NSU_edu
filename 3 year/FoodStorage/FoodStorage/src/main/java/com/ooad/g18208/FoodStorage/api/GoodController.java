package com.ooad.g18208.FoodStorage.api;

import com.ooad.g18208.FoodStorage.model.GoodItem;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IGoodRepository;
import com.ooad.g18208.FoodStorage.exception.CreateException;
import com.ooad.g18208.FoodStorage.exception.LackOfIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController

public class GoodController {
    private final String get_ = "/get";
    private final String delete_ = "/delete";
    private final String update_ = "/update";
    private final String create_ = "/create";
    private final String getAll_ = "/get/all";
    private final String good_ = "/api/good";
    private IGoodRepository rep;

    @Autowired
    GoodController(IGoodRepository rep) {
        this.rep = rep;
    }

    @GetMapping(good_ + get_ + "/{goodItemId}")
    public ResponseEntity<GoodItem> getGood(
            @PathVariable String goodItemId) throws LackOfIdException {
        GoodItem goodItem = rep.getGood(goodItemId);
        if(goodItem==null) throw new LackOfIdException();

        return ResponseEntity.ok(goodItem);
    }

    @GetMapping(good_ + getAll_)
    public ResponseEntity<Collection<GoodItem>> getGoods() throws LackOfIdException {
        Collection<GoodItem> goodItems = rep.getGoods();
        if(goodItems==null) throw new LackOfIdException();

        return ResponseEntity.ok(goodItems);
    }

    @PatchMapping(good_ + update_ + "/{goodItemId}")
    public ResponseEntity<GoodItem> updateGood(
            @PathVariable String goodItemId,
            @RequestBody GoodItem goodItem
    ) throws LackOfIdException {
        GoodItem updatedGood = rep.updateGood(goodItemId, goodItem);
        if(updatedGood==null) throw new LackOfIdException();

        return ResponseEntity.ok(updatedGood);
    }

    @PostMapping(good_ + create_)
    public ResponseEntity<GoodItem> addGood(
            @RequestBody GoodItem goodItem) {
        GoodItem result = rep.createGood(goodItem);
        if(result==null) throw new CreateException();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(good_ + delete_ + "/{goodItemId}")
    public ResponseEntity<?> deleteGood(
            @PathVariable String goodItemId) {
        rep.deleteGood(goodItemId);
        return ResponseEntity.ok().build();
    }

}
