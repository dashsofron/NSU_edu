package com.ooad.g18208.FoodStorage.api;

import com.ooad.g18208.FoodStorage.model.Recipe;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IRecipeRepository;
import com.ooad.g18208.FoodStorage.exception.CreateException;
import com.ooad.g18208.FoodStorage.exception.LackOfIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController

public class RecipeController {
    private final String get_ = "/get";
    private final String delete_ = "/delete";
    private final String update_ = "/update";
    private final String create_ = "/create";
    private final String getAll_ = "/get/all";
    private final String recipe_ = "/api/recipe";
    private IRecipeRepository rep;

    @Autowired
    RecipeController(IRecipeRepository rep) {
        this.rep = rep;
    }

    @GetMapping(recipe_ + get_ + "/{recipeId}")
    public ResponseEntity<Recipe> getRecipe(
            @PathVariable String recipeId) {
        Recipe recipe = rep.getRecipe(recipeId);
        if(recipe==null) throw new LackOfIdException();

        return ResponseEntity.ok(recipe);
    }

    @GetMapping(recipe_ + getAll_)
    public ResponseEntity<Collection<Recipe>> getRecipes() {
        Collection<Recipe> recipes = rep.getRecipes();
        if(recipes==null) throw new LackOfIdException();

        return ResponseEntity.ok(recipes);
    }

    @PatchMapping(recipe_ + update_ + "/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable String recipeId,
            @RequestBody Recipe recipe
    ) {
        Recipe updatedRecipe = rep.updateRecipe(recipeId, recipe);
        if(updatedRecipe==null) throw new LackOfIdException();

        return ResponseEntity.ok(updatedRecipe);
    }

    @PostMapping(recipe_ + create_)
    public ResponseEntity<Recipe> addRecipe(
            @RequestBody Recipe recipe) {
        Recipe result = rep.createRecipe(recipe);
        if(result==null) throw new CreateException();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(recipe_ + delete_ + "/{recipeId}")
    public ResponseEntity<?> deleteRecipe(
            @PathVariable String recipeId) {
        rep.deleteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }

}
