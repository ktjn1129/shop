package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.FavoriteListBean;
import com.example.demo.model.Favorite;
import com.example.demo.repository.FavoriteDao;

/**
 * お気に入り商品Dao JDBCクラス
 * 
 * @author koto
 */
@Repository("FavoriteDaoJdbc")
public class FavoriteDaoJdbc implements FavoriteDao {
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * お気に入り商品情報の登録処理
	 * 
	 * @param favorite お気に入り商品情報
	 */
	public void addFavorite(Favorite favorite) throws DataAccessException {
		
		String sql = "INSERT INTO favorites (user_id, item_id)" + " VALUES(?, ?)";
		
		jdbcTemplate.update(sql, favorite.getUserId(), favorite.getItemId());
	}
	
	/**
	 * お気に入り商品情報の削除処理
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 */
	public void removeFavorite(int userId, int itemId) throws DataAccessException {
		
		String sql = "DELETE FROM favorites" + " WHERE user_id = ? AND item_id = ?";
		
		jdbcTemplate.update(sql, userId, itemId);
	}
	
	/**
	 * お気に入りリストの取得
	 * 
	 * @param userId ユーザーID
	 * @return favoriteList 取得したお気に入りリスト
	 */
	public List<FavoriteListBean> findByUserId(int userId) throws DataAccessException {
		
		String sql = "SELECT * FROM favorites AS l"
					+ " LEFT JOIN items AS i ON l.item_id = i.id"
					+ " WHERE user_id = ?";
		
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(sql, userId);
		List<FavoriteListBean> favoriteList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			FavoriteListBean favoriteItem = new FavoriteListBean();
			favoriteItem.setItemId((Integer) map.get("item_id"));
			favoriteItem.setName((String) map.get("name"));
			favoriteItem.setPrice((Integer) map.get("price"));
			favoriteItem.setDescription((String) map.get("description"));
			favoriteItem.setImage((String) map.get("image"));
			favoriteList.add(favoriteItem);
		}
		return favoriteList;
	}
}