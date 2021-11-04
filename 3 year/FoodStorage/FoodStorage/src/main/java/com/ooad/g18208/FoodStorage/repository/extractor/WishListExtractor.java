package com.ooad.g18208.FoodStorage.repository.extractor;

import com.ooad.g18208.FoodStorage.model.NeededGood;
import com.ooad.g18208.FoodStorage.model.WishList;
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
public class WishListExtractor implements ResultSetExtractor<List<WishList>> {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private WishListGoodExtractor neededGoodExtractor;

    @Autowired
    public WishListExtractor(WishListGoodExtractor neededGoodExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.neededGoodExtractor = neededGoodExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WishList> extractData(ResultSet rs) throws SQLException {
        Map<String, WishList> wishLists = new HashMap<>();
        while (rs.next()) {
            String listID = rs.getString("WISH_LIST_ID");
            WishList list;
            if (wishLists.containsKey(listID))
                list = wishLists.get(listID);
            else {
                list = new WishList();
                list.setId(rs.getInt("WISH_LIST_ID"));
                list.setName(rs.getString("WISH_LIST_NAME"));
                String goodsSql = "select WISH_LIST_ID, GOOD_TYPE_ID , AMOUNT " +
                        "from WISH_LIST_GOODS " +
                        "where WISH_LIST_ID=:listID";
                MapSqlParameterSource goodsParams = new MapSqlParameterSource()
                        .addValue("listID", listID);
                List<NeededGood> goods = jdbcTemplate.query(goodsSql, goodsParams, neededGoodExtractor);
                if (!(goods == null || goods.isEmpty())) {
                    list.setGood(goods);
                }
            }
            wishLists.put(listID, list);

        }

        return new ArrayList<>(wishLists.values());
    }
}
