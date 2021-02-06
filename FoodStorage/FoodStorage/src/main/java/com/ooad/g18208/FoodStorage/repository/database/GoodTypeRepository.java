package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.GoodType;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IGoodTypeRepository;
import com.ooad.g18208.FoodStorage.repository.extractor.GoodTypeExtractor;
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
@ConditionalOnProperty(name = "goodType.database", havingValue = "true")
public class GoodTypeRepository implements IGoodTypeRepository {
    private GoodTypeExtractor goodTypeExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GoodTypeRepository(GoodTypeExtractor goodTypeExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.goodTypeExtractor = goodTypeExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public GoodType getGoodType(String goodTypeID) {
        String provideSql = "select GOOD_TYPE_ID, CATEGORY, SUBCATEGORY,VITALITY " +
                "from GOOD_TYPES " +
                "where GOOD_TYPE_ID=:goodTypeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodTypeID", goodTypeID);
        List<GoodType> lists = jdbcTemplate.query(provideSql, params, goodTypeExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }

    @Override
    public Collection<GoodType> getGoodTypes() {
        String provideAllSql = "select GOOD_TYPE_ID,CATEGORY, SUBCATEGORY,VITALITY " +
                "from GOOD_TYPES ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, goodTypeExtractor);
    }

    @Override
    public void deleteGoodType(String goodTypeID) {
        String deleteSql = "delete from GOOD_TYPES where GOOD_TYPE_ID=:goodTypeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodTypeID", goodTypeID);
        jdbcTemplate.update(deleteSql, params);
    }

    @Override
    public GoodType updateGoodType(String goodTypeID, GoodType goodType) {
        String updateSql = "update GOOD_TYPES " +
                "set CATEGORY=:category, " +
                "SUBCATEGORY=:subcategory, " +
                "VITALITY=:vitality " +
                "where GOOD_TYPE_ID=:goodTypeID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodTypeID", goodTypeID)
                .addValue("category", goodType.getCategory())
                .addValue("subcategory", goodType.getSubcategory())
                .addValue("vitality", goodType.getVitality());
        jdbcTemplate.update(updateSql, params);
        return getGoodType(goodTypeID);


    }

    @Override
    public GoodType createGoodType(GoodType goodType) {
        String insertSql = "insert into GOOD_TYPES (CATEGORY, SUBCATEGORY,VITALITY) values (:category,:subcategory,:vitality)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("category", goodType.getCategory())
                .addValue("subcategory", goodType.getSubcategory())
                .addValue("vitality", goodType.getVitality());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSql, params, generatedKeyHolder);
        Integer goodTypeID = Integer.parseInt(generatedKeyHolder.getKeys().get("GOOD_TYPE_ID").toString());
        goodType.setTypeId(goodTypeID);

        return getGoodType(goodTypeID.toString());
    }
}
