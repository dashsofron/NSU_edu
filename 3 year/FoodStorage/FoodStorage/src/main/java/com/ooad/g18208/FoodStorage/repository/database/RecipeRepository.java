package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.Recipe;
import com.ooad.g18208.FoodStorage.repository.extractor.RecipeExtractor;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "recipe.database", havingValue = "true")
public class RecipeRepository implements IRecipeRepository {
    private RecipeExtractor recipeExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RecipeRepository(RecipeExtractor recipeExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.recipeExtractor = recipeExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Recipe getRecipe(String recipeID) {
        String provideSql = "select RECIPE_ID, NAME, TIME,DIFFICULTY,RULE " +
                "from RECIPES " +
                "where RECIPE_ID=:recipeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("recipeID", recipeID);
        List<Recipe> lists = jdbcTemplate.query(provideSql, params, recipeExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }

    @Override
    public Collection<Recipe> getRecipes() {
        String provideAllSql = "select NAME, TIME,DIFFICULTY,RULE " +
                "from RECIPES ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, recipeExtractor);
    }

    @Override
    public void deleteRecipe(String recipeID) {
        String deleteSql = "delete from RECIPES where RECIPE_ID=:recipeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("recipeID", recipeID);
        jdbcTemplate.update(deleteSql, params);
    }

    @Override
    public Recipe updateRecipe(String recipeID, Recipe recipe) {

        String updateSql = "update RECIPES " +
                "set NAME=:name, " +
                "TIME=:time, " +
                "DIFFICULTY=:difficulty, " +
                "RULE=:rule " +
                "where RECIPE_ID=:recipeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("recipeID", recipeID)
                .addValue("name", recipe.getName())
                .addValue("time", recipe.getTime())
                .addValue("difficulty", recipe.getDifficulty())
                .addValue("rule", recipe.getRule());
        jdbcTemplate.update(updateSql, params);
        return getRecipe(recipeID);

    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        String insertSql = "insert into RECIPES (NAME, TIME,DIFFICULTY,RULE) values (:name,:time,:difficulty,:rule)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", recipe.getName())
                .addValue("time", recipe.getTime())
                .addValue("difficulty", recipe.getDifficulty())
                .addValue("rule", recipe.getRule());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSql, params, generatedKeyHolder);
        Integer recipeID = Integer.parseInt(generatedKeyHolder.getKeys().get("RECIPE_ID").toString());
        recipe.setId(recipeID);

        return getRecipe(recipeID.toString());
    }
}
