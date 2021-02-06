package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.NeededGood;
import com.ooad.g18208.FoodStorage.repository.extractor.RecipeGoodExtractor;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IRecipeGoodRepository;
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
@ConditionalOnProperty(name = "recipeGood.database", havingValue = "true")
public class RecipeGoodRepository implements IRecipeGoodRepository {
    private RecipeGoodExtractor goodExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RecipeGoodRepository(RecipeGoodExtractor goodExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.goodExtractor = goodExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public NeededGood getRecipeGood(String goodID) {
        String provideSql = "select RECIPE_GOOD_ID, GOOD_TYPE_ID, RECIPE_ID,AMOUNT " +
                "from RECIPE_GOODS " +
                "where RECIPE_GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        List<NeededGood> lists = jdbcTemplate.query(provideSql, params, goodExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }

    @Override
    public Collection<NeededGood> getRecipeGoods() {
        String provideAllSql = "select GOOD_TYPE_ID, RECIPE_ID,AMOUNT " +
                "from RECIPE_GOODS ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, goodExtractor);
    }

    @Override
    public void deleteRecipeGood(String goodID) {
        String deleteSql = "delete from RECIPE_GOODS where RECIPE_GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        jdbcTemplate.update(deleteSql, params);
    }

    @Override
    public NeededGood updateRecipeGood(String goodID, String recipeID, NeededGood good) {

        String updateSql = "update RECIPE_GOODS " +
                "set GOOD_TYPE_ID=:goodType, " +
                "RECIPE_ID=:recipeID, " +
                "AMOUNT=:amount " +
                "where GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID)
                .addValue("goodType", good.getTypeId())
                .addValue("recipeID", recipeID)
                .addValue("amount", good.getAmount());
        jdbcTemplate.update(updateSql, params);
        return getRecipeGood(goodID);

    }

    @Override
    public NeededGood createRecipeGood(NeededGood good, String recipeID) {
        String insertSql = "insert into RECIPE_GOODS (GOOD_TYPE_ID, WISH_LIST_ID,AMOUNT) values (:goodType,:recipeID,:amount)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodType", good.getTypeId())
                .addValue("recipeId", recipeID)
                .addValue("amount", good.getAmount());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSql, params, generatedKeyHolder);
        Integer goodID = Integer.parseInt(generatedKeyHolder.getKeys().get("RECIPE_GOOD_ID").toString());
        good.setId(goodID);

        return getRecipeGood(goodID.toString());
    }
}