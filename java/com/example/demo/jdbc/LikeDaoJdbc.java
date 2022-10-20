package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.LikeListBean;
import com.example.demo.model.Like;
import com.example.demo.repository.LikeDao;

/**
 * お気に入り商品Dao JDBCクラス
 * 
 * @author koto
 */
@Repository("LikeDaoJdbc")
public class LikeDaoJdbc implements LikeDao {
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * お気に入り商品情報の登録処理
	 * 
	 * @param like お気に入り商品情報
	 */
	public void addLike(Like like) throws DataAccessException {
		//　SQL文を定義
		String sql = "INSERT INTO likes (user_id, item_id)" + " VALUES(?, ?)";
		// クエリを実行
		jdbcTemplate.update(sql, like.getUserId(), like.getItemId());
	}
	
	/**
	 * お気に入り商品情報の削除処理
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 */
	public void removeLike(int userId, int itemId) throws DataAccessException {
		//　SQL文を定義
		String sql = "DELETE FROM likes" + " WHERE user_id = ? AND item_id = ?";
		// クエリを実行
		jdbcTemplate.update(sql, userId, itemId);
	}
	
	/**
	 * お気に入りリストの取得
	 * 
	 * @param userId ユーザーID
	 * @return likeList 取得したお気に入りリスト
	 */
	public List<LikeListBean> findByUserId(int userId) throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT * FROM likes AS l"
					+ " LEFT JOIN items AS i ON l.item_id = i.id"
					+ " WHERE user_id = ?";
		// クエリを実行しお気に入り情報を取得
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(sql, userId);
		// 返却用のお気に入りリストを生成
		List<LikeListBean> likeList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Likeインスタンスを生成
			LikeListBean likeItem = new LikeListBean();
			// 取得したお気に入り商品情報をセット
			likeItem.setItemId((Integer) map.get("item_id"));
			likeItem.setName((String) map.get("name"));
			likeItem.setPrice((Integer) map.get("price"));
			likeItem.setDescription((String) map.get("description"));
			likeItem.setImage((String) map.get("image"));
			// お気に入りリストに追加
			likeList.add(likeItem);
		}
		return likeList;
	}
}
