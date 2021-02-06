package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.NeededGood;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IWishListGoodRepository;
import com.ooad.g18208.FoodStorage.repository.extractor.WishListGoodExtractor;
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
@ConditionalOnProperty(name = "wihListGood.database", havingValue = "true")
public class WishListGoodRepository implements IWishListGoodRepository {
    private WishListGoodExtractor goodExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public WishListGoodRepository(WishListGoodExtractor goodExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.goodExtractor = goodExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public NeededGood getWishListGood(String goodID) {
        String provideSql = "select GOOD_TYPE_ID, WISH_LIST_ID,AMOUNT " +
                "from WISH_LIST_GOODS " +
                "where WISH_LIST_GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        List<NeededGood> lists = jdbcTemplate.query(provideSql, params, goodExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }

    @Override
    public Collection<NeededGood> getWishListGoods() {
        String provideAllSql = "select GOOD_TYPE_ID, WISH_LIST_ID,AMOUNT " +
                "from WISH_LIST_GOODS ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, goodExtractor);
    }

    @Override
    public void deleteWishListGood(String goodID) {
        String deleteSql = "delete from WISH_LIST_GOODS where WISH_LIST_GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID);
        jdbcTemplate.update(deleteSql, params);
    }

    @Override
    public NeededGood updateWishListGood(String goodID, String listID, NeededGood good) {
        String updateSql = "update WISH_LIST_GOODS " +
                "set GOOD_TYPE_ID=:goodType, " +
                "WISH_LIST_ID=:listID, " +
                "AMOUNT=:amount " +
                "where GOOD_ID=:goodID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodID", goodID)
                .addValue("goodType", good.getTypeId())
                .addValue("listID", listID)
                .addValue("amount", good.getAmount());
        jdbcTemplate.update(updateSql, params);
        return getWishListGood(goodID);

    }


    @Override
    public NeededGood createWishListGood(NeededGood good, String listID) {
        String insertSql = "insert into WISH_LIST_GOODS (GOOD_TYPE_ID, WISH_LIST_ID,AMOUNT) values (:goodType,:listID,:amount)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("goodType", good.getTypeId())
                .addValue("listID", listID)
                .addValue("amount", good.getAmount());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSql, params, generatedKeyHolder);
        Integer goodID = Integer.parseInt(generatedKeyHolder.getKeys().get("WISH_LIST_GOOD_ID").toString());
        good.setId(goodID);

        return getWishListGood(goodID.toString());
    }
}
