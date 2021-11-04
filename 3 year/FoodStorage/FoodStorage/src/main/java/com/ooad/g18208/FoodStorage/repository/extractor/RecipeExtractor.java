package com.ooad.g18208.FoodStorage.repository.extractor;

import com.ooad.g18208.FoodStorage.model.Recipe;
import com.ooad.g18208.FoodStorage.model.NeededGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RecipeExtractor implements ResultSetExtractor<List<Recipe>> {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private WishListGoodExtractor neededGoodExtractor;

    @Autowired
    public RecipeExtractor(WishListGoodExtractor neededGoodExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.neededGoodExtractor = neededGoodExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Recipe> extractData(ResultSet rs) throws SQLException {
        Map<String, Recipe> recipes = new HashMap<>();
        while (rs.next()) {
            String recipeID = rs.getString("RECIPE_ID");
            Recipe recipe;
            if (recipes.containsKey(recipeID))
                recipe = recipes.get(recipeID);
            else {
                recipe = new Recipe();
                recipe.setId(rs.getInt("RECIPE_ID"));
                recipe.setName(rs.getString("NAME"));
                recipe.setTime(rs.getLong("TIME"));
                recipe.setDifficulty(rs.getInt("DIFFICULTY"));
                recipe.setRule(rs.getString("RULE"));

                String goodsSql = "select RECIPE_ID, GOOD_TYPE_ID , AMOUNT " +
                        "from RECIPE_GOODS " +
                        "where RECIPE_ID=:recipeID";
                MapSqlParameterSource goodsParams = new MapSqlParameterSource()
                        .addValue("recipeID", recipeID);
                List<NeededGood> goods = jdbcTemplate.query(goodsSql, goodsParams, neededGoodExtractor);
                if (!(goods == null || goods.isEmpty())) {
                    recipe.setIngredients(goods);
                }
            }
            recipes.put(recipeID, recipe);

        }

        return new ArrayList<>(recipes.values());
    }

}
