package com.ooad.g18208.FoodStorage.repository.repositoryInterface;

import com.ooad.g18208.FoodStorage.model.Recipe;

import java.util.Collection;

public interface IRecipeRepository {
    Recipe getRecipe(String recipeId);

    Collection<Recipe> getRecipes();

    void deleteRecipe(String recipeId);

    Recipe updateRecipe(String recipeId, Recipe recipe);

    Recipe createRecipe(Recipe recipe);
}
