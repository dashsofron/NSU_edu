package com.ooad.g18208.FoodStorage.repository.extractor;

import com.ooad.g18208.FoodStorage.model.GoodType;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GoodTypeExtractor implements ResultSetExtractor<List<GoodType>> {

    @Override
    public List<GoodType> extractData(ResultSet rs) throws SQLException {
        Map<String, GoodType> goodTypes = new HashMap<>();
        while (rs.next()) {
            String goodTypeID = rs.getString("GOOD_TYPE_ID");
            GoodType goodType;
            if (goodTypes.containsKey(goodTypeID))
                goodType = goodTypes.get(goodTypeID);
            else {
                goodType = new GoodType();
                goodType.setTypeId(rs.getInt("GOOD_TYPE_ID"));
                goodType.setCategory(rs.getString("CATEGORY"));
                goodType.setSubcategory(rs.getString("SUBCATEGORY"));
                goodType.setVitality(rs.getBoolean("VITALITY"));

            }
            goodTypes.put(goodTypeID, goodType);

        }

        return new ArrayList<>(goodTypes.values());
    }

}
