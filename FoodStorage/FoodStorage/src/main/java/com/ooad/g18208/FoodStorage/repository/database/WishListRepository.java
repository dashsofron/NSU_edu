package com.ooad.g18208.FoodStorage.repository.database;

import com.ooad.g18208.FoodStorage.model.WishList;
import com.ooad.g18208.FoodStorage.repository.extractor.WishListExtractor;
import com.ooad.g18208.FoodStorage.repository.repositoryInterface.IWishListRepository;
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
@ConditionalOnProperty(name = "wishList.database", havingValue = "true")
public class WishListRepository implements IWishListRepository {
    private WishListExtractor wishListExtractor;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public WishListRepository(WishListExtractor wishListExtractor, NamedParameterJdbcTemplate jdbcTemplate) {
        this.wishListExtractor = wishListExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public WishList getWishList(String listID) {
        String provideSql = "select  WISH_LIST_ID, WISH_LIST_NAME " +
                "from WISH_LISTS " +
                "where WISH_LIST_ID=:listID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("listID", listID);
        List<WishList> lists = jdbcTemplate.query(provideSql, params, wishListExtractor);
        if (lists.isEmpty()) {
            return null;
        }

        return lists.get(0);
    }


    @Override
    public Collection<WishList> getWishLists() {
        String provideAllSql = "select WISH_LIST_ID, WISH_LIST_NAME " +
                "from WISH_LISTS ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.query(provideAllSql, params, wishListExtractor);
    }

    @Override
    public void deleteWishList(String listID) {
        String deleteListSql = "delete from WISH_LISTS where WISH_LIST_ID=:listID";
        String deleteGoodSql = "delete from WISH_LIST_GOODS where WISH_LIST_ID=:listID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("listID", listID);
        jdbcTemplate.update(deleteListSql, params);
        jdbcTemplate.update(deleteGoodSql, params);
    }

    @Override
    public WishList updateWishList(String listID, WishList wishList) {

        String updateSql = "update WISH_LISTS " +
                "set WISH_LIST_NAME=:name " +
                "where WISH_LIST_ID=:listID";
        MapSqlParameterSource listParams = new MapSqlParameterSource()
                .addValue("listID",listID)
                .addValue("name", wishList.getName());
        jdbcTemplate.update(updateSql, listParams);
        return getWishList(listID);

    }

    @Override
    public WishList createWishList(WishList wishList) {
        String insertWishListSql = "insert into WISH_LISTS (WISH_LIST_NAME) values (:name)";
        MapSqlParameterSource listParams = new MapSqlParameterSource()
                .addValue("name", wishList.getName());
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertWishListSql, listParams, generatedKeyHolder);
        Integer listID = Integer.parseInt(generatedKeyHolder.getKeys().get("WISH_LIST_ID").toString());
        wishList.setId(listID);

        return getWishList(listID.toString());
    }

}
