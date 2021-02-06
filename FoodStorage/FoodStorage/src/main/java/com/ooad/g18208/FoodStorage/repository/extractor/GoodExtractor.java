package com.ooad.g18208.FoodStorage.repository.extractor;

import com.ooad.g18208.FoodStorage.model.GoodItem;
import com.ooad.g18208.FoodStorage.model.GoodType;
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
public class GoodExtractor implements ResultSetExtractor<List<GoodItem>> {
    private GoodTypeExtractor goodTypeExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GoodExtractor(GoodTypeExtractor goodTypeExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.goodTypeExtractor = goodTypeExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GoodItem> extractData(ResultSet rs) throws SQLException {
        Map<String, GoodItem> goods = new HashMap<>();
        while (rs.next()) {
            String goodID = rs.getString("GOOD_ID");
            GoodItem good;
            if (goods.containsKey(goodID))
                good = goods.get(goodID);
            else {
                good = new GoodItem();
                good.setTypeId(rs.getInt("GOOD_ID"));
                good.setName(rs.getString("NAME"));
                good.setProducer(rs.getString("PRODUCER"));
                good.setDaysBeforeSpoiled(rs.getInt("DAYS_BEFORE_SPOILED"));
                good.setCalories(rs.getInt("CALORIES"));
                good.setCarbohydrate(rs.getFloat("CARBOHYDRATE"));
                good.setFat(rs.getFloat("FAT"));
                good.setProtein(rs.getFloat("PROTEIN"));
                int goodTypeID = rs.getInt("GOOD_TYPE_ID");
                String sql = "select GOOD_TYPE_ID, CATEGORY, SUBCATEGORY,VITALITY " +
                        "from GOOD_TYPES " +
                        "where GOOD_TYPE_ID=:goodTypeID";
                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("goodTypeID", goodTypeID);
                List<GoodType> goodT = jdbcTemplate.query(sql, params, goodTypeExtractor);
                if (!(goodT == null || goodT.isEmpty())) good.setGoodType(goodT.get(0));
            }
            goods.put(goodID, good);

        }

        return new ArrayList<>(goods.values());
    }
}
