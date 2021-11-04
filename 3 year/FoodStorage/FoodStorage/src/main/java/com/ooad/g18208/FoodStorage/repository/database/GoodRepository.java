package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.GoodItem;
import com.ooad.g18208.FoodStorage.repository.extractor.GoodExtractor;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IGoodRepository;
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
@ConditionalOnProperty(name = "good.database", havingValue = "true")
public class GoodRepository implements IGoodRepository {
    private GoodExtractor goodExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GoodRepository(GoodExtractor goodExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.goodExtractor = goodExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public GoodItem getGood(String goodID) {
        String provideSql = "select GOOD_ID,GOOD_TYPE_ID, DAYS_BEFORE_SPOILED,NAME,PRODUCER,PROTEIN,FAT,CARBOHYDRATE,CALORIES " +
                "from GOODS " +
                "where GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        List<GoodItem> lists = jdbcTemplate.query(provideSql, params, goodExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }

    @Override
    public Collection<GoodItem> getGoods() {
        String provideAllSql = "select GOOD_ID,GOOD_TYPE_ID, DAYS_BEFORE_SPOILED,NAME,PRODUCER,PROTEIN,FAT,CARBOHYDRATE,CALORIES " +
                "from GOODS ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, goodExtractor);
    }

    @Override
    public void deleteGood(String goodID) {
        String deleteSql = "delete from GOODS where GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        jdbcTemplate.update(deleteSql, params);
    }

    @Override
    public GoodItem updateGood(String goodID, GoodItem good) {

        String updateSql = "update GOODS " +
                "set GOOD_TYPE_ID=:goodType, " +
                "DAYS_BEFORE_SPOILED=:daysBeforeSpoiled, " +
                "NAME=:name, " +
                "PRODUCER=:producer, " +
                "PROTEIN=:protein ," +
                "FAT=:fat ," +
                "CARBOHYDRATE=:carbohydrate ," +
                "CALORIES=:calories " +
                "where GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", good.getId())
                .addValue("goodType", good.getTypeId())
                .addValue("daysBeforeSpoiled", good.getDaysBeforeSpoiled())
                .addValue("name", good.getName())
                .addValue("producer", good.getProducer())
                .addValue("protein", good.getProtein())
                .addValue("fat", good.getFat())
                .addValue("carbohydrate", good.getCarbohydrate())
                .addValue("calories", good.getCalories());
        jdbcTemplate.update(updateSql, params);
        return getGood(goodID);
    }

    @Override
    public GoodItem createGood(GoodItem good) {
        String insertSql = "insert into GOODS (GOOD_TYPE_ID, DAYS_BEFORE_SPOILED,NAME,PRODUCER,PROTEIN,FAT,CARBOHYDRATE,CALORIES) " +
                "values (:goodType,:daysBeforeSpoiled,:name,:producer,:protein,:fat,:carbohydrate,:calories)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodType", good.getTypeId())
                .addValue("daysBeforeSpoiled", good.getDaysBeforeSpoiled())
                .addValue("name", good.getName())
                .addValue("producer", good.getProducer())
                .addValue("protein", good.getProtein())
                .addValue("fat", good.getFat())
                .addValue("carbohydrate", good.getCarbohydrate())
                .addValue("calories", good.getCalories());


        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSql, params, generatedKeyHolder);
        Integer goodID = Integer.parseInt(generatedKeyHolder.getKeys().get("GOOD_ID").toString());
        good.setTypeId(goodID);

        return getGood(goodID.toString());
    }
}
