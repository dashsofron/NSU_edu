package com.ooad.g18208.FoodStorage.api;

import com.ooad.g18208.FoodStorage.model.GoodType;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IGoodTypeRepository;
import com.ooad.g18208.FoodStorage.exception.CreateException;
import com.ooad.g18208.FoodStorage.exception.LackOfIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class GoodTypeController {
    private final String get_ = "/get";
    private final String delete_ = "/delete";
    private final String update_ = "/update";
    private final String create_ = "/create";
    private final String getAll_ = "/get/all";
    private final String goodType_ = "/api/goodType";
    private IGoodTypeRepository rep;

    @Autowired
    GoodTypeController(IGoodTypeRepository rep) {
        this.rep = rep;

    }

    @GetMapping(goodType_ + get_ + "/{goodTypeId}")
    public ResponseEntity<GoodType> getGoodType(
            @PathVariable String goodTypeId) {
        GoodType goodType = rep.getGoodType(goodTypeId);
        if(goodType==null) throw new LackOfIdException();

        return ResponseEntity.ok(goodType);
    }

    @GetMapping(goodType_ + getAll_)
    public ResponseEntity<Collection<GoodType>> getGoodTypes() {
        Collection<GoodType> goodTypes = rep.getGoodTypes();
        if(goodTypes==null) throw new LackOfIdException();

        return ResponseEntity.ok(goodTypes);
    }

    @PatchMapping(goodType_ + update_ + "/{goodTypeId}")
    public ResponseEntity<GoodType> updateGoodType(
            @PathVariable String goodTypeId,
            @RequestBody GoodType goodType
    ) {
        GoodType updatedGoodType = rep.updateGoodType(goodTypeId, goodType);
        if(updatedGoodType==null) throw new LackOfIdException();

        return ResponseEntity.ok(updatedGoodType);
    }

    @PostMapping(goodType_ + create_)
    public ResponseEntity<GoodType> addGoodType(
            @RequestBody GoodType goodType) {
        GoodType result = rep.createGoodType(goodType);
        if(result==null) throw new CreateException();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(goodType_ + delete_ + "/{goodTypeId}")
    public ResponseEntity<?> deleteGoodType(
            @PathVariable String goodTypeId) {
        rep.deleteGoodType(goodTypeId);
        return ResponseEntity.ok().build();
    }
}
