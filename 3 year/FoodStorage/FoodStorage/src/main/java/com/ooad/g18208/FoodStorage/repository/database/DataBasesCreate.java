package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.GoodItem;
import com.ooad.g18208.FoodStorage.model.GoodType;
import com.ooad.g18208.FoodStorage.model.WishList;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DataBasesCreate {
    private NamedParameterJdbcTemplate jdbcTemplate;
    IGoodTypeRepository goodTypeRepository;
    IGoodRepository goodRepository;
    IRecipeRepository recipeRepository;
    IRecipeGoodRepository recipeGoodRepository;
    IWishListRepository wishListRepository;
    IWishListGoodRepository wishListGoodRepository;
    DataBasesCreate(NamedParameterJdbcTemplate jdbcTemplate, IGoodTypeRepository goodTypeRepository, IGoodRepository goodRepository,
                    IRecipeRepository recipeRepository, IRecipeGoodRepository recipeGoodRepository, IWishListRepository wishListRepository, IWishListGoodRepository wishListGoodRepository){
        this.jdbcTemplate=jdbcTemplate;
        this.goodRepository=goodRepository;
        this.goodTypeRepository=goodTypeRepository;
        this.recipeGoodRepository=recipeGoodRepository;
        this.recipeRepository=recipeRepository;
        this.wishListGoodRepository=wishListGoodRepository;
        this.wishListRepository=wishListRepository;
    }
    @PostConstruct
    public void initialize() {
        String createGenerateGoodTypeSequenceSql = "create sequence GOOD_TYPE_ID_GENERATOR";
        String createGoodTypeTableSql = "create table GOOD_TYPES (" +
                "GOOD_TYPE_ID INTEGER default GOOD_TYPE_ID_GENERATOR.nextval," +
                "CATEGORY VARCHAR(64)," +
                "SUBCATEGORY VARCHAR(64)," +
                "VITALITY BOOLEAN" +
                ");";
        jdbcTemplate.update(createGenerateGoodTypeSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createGoodTypeTableSql, new MapSqlParameterSource());
        goodTypeRepository.createGoodType(new GoodType("Молочное изделие", "сыр"));

        String createGenerateGoodSequenceSql = "create sequence GOOD_ID_GENERATOR";
        String createGoodTableSql = "create table GOODS (" +
                "GOOD_ID INTEGER default GOOD_ID_GENERATOR.nextval," +
                "GOOD_TYPE_ID INTEGER," +
                "DAYS_BEFORE_SPOILED INTEGER," +
                "NAME VARCHAR(64)," +
                "PRODUCER VARCHAR(64)," +
                "PROTEIN FLOAT," +
                "FAT FLOAT," +
                "CARBOHYDRATE FLOAT," +
                "CALORIES INTEGER" +
                ");";
        jdbcTemplate.update(createGenerateGoodSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createGoodTableSql, new MapSqlParameterSource());
        goodRepository.createGood(new GoodItem(1, 10, "Творожный сыр", "Hohland", 6.6F, 19.5F, 3.7F, 217));
        goodRepository.createGood(new GoodItem(2, 10, "Творожный сыр", "Hohland", 6.6F, 19.5F, 3.7F, 217, "Молочное изделие", "Сыр"));


        String createGenerateRecipeGoodSequenceSql = "create sequence RECIPE_GOOD_ID_GENERATOR";
        String createRecipeGoodTableSql = "create table RECIPE_GOODS (" +
                "RECIPE_GOOD_ID INTEGER default RECIPE_GOOD_ID_GENERATOR.nextval," +
                "GOOD_TYPE_ID INTEGER," +
                "RECIPE_ID INTEGER," +
                "AMOUNT  INTEGER" +
                ");";
        jdbcTemplate.update(createGenerateRecipeGoodSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createRecipeGoodTableSql, new MapSqlParameterSource());


        String createGenerateRecipeSequenceSql = "create sequence RECIPE_ID_GENERATOR";
        String createRecipeTableSql = "create table RECIPES (" +
                "RECIPE_ID INTEGER default RECIPE_ID_GENERATOR.nextval," +
                "NAME VARCHAR(64)," +
                "TIME LONG," +
                "DIFFICULTY INTEGER," +
                "RULE VARCHAR" +
                ");";
        jdbcTemplate.update(createGenerateRecipeSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createRecipeTableSql, new MapSqlParameterSource());



        String createGenerateNeededGoodSequenceSql = "create sequence WISH_LIST_GOOD_ID_GENERATOR";
        String createNeededGoodTableSql = "create table WISH_LIST_GOODS (" +
                "WISH_LIST_GOOD_ID INTEGER default WISH_LIST_GOOD_ID_GENERATOR.nextval," +
                "GOOD_TYPE_ID INTEGER," +
                "WISH_LIST_ID INTEGER," +
                "AMOUNT  INTEGER" +
                ");";
        jdbcTemplate.update(createGenerateNeededGoodSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createNeededGoodTableSql, new MapSqlParameterSource());

        String createGenerateWishListIdSequenceSql = "create sequence WISH_LIST_ID_GENERATOR";
        String createWishListTableSql = "create table WISH_LISTS (" +
                "WISH_LIST_ID   INTEGER default WISH_LIST_ID_GENERATOR.nextval," +
                "WISH_LIST_NAME  VARCHAR(64)" +
                ");";
        jdbcTemplate.update(createGenerateWishListIdSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createWishListTableSql, new MapSqlParameterSource());
        wishListRepository.createWishList(new WishList(1, "first"));
        wishListRepository.createWishList(new WishList(2, "second"));



    }
}
