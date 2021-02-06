package com.ooad.g18208.FoodStorage.repository.extractor;

import com.ooad.g18208.FoodStorage.model.GoodType;
import com.ooad.g18208.FoodStorage.model.NeededGood;
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
public class RecipeGoodExtractor implements ResultSetExtractor<List<NeededGood>> {
    private GoodTypeExtractor goodTypeExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<NeededGood> extractData(ResultSet rs) throws SQLException {
        Map<String, NeededGood> goods = new HashMap<>();
        while (rs.next()) {
            String goodID = rs.getString("RECIPE_GOOD_ID");
            NeededGood good;
            if (goods.containsKey(goodID))
                good = goods.get(goodID);
            else {
                good = new NeededGood();
                good.setId(rs.getInt("RECIPE_GOOD_ID"));
                good.setAmount(rs.getInt("AMOUNT"));
                int goodTypeID = rs.getInt("GOOD_TYPE_ID");
                String sql = "select GOOD_TYPE_ID, CATEGORY, SUBCATEGORY,VITALITY " +
                        "from GOOD_TYPES " +
                        "where GOOD_TYPE_ID=:goodTypeID";
                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("goodTypeID", goodTypeID);
                List<GoodType> goodT = jdbcTemplate.query(sql, params, goodTypeExtractor);
                if (!goodT.isEmpty()) good.setGoodType(goodT.get(0));

            }
            goods.put(goodID, good);

        }

        return new ArrayList<>(goods.values());
    }
}


