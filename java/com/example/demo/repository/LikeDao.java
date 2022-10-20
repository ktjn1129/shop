package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.bean.LikeListBean;
import com.example.demo.model.Like;

/**
 * お気に入り商品Dao
 * 
 * @author koto
 */
public interface LikeDao {
	
	// お気に入り商品情報を登録
	public void addLike(Like like) throws DataAccessException;
	
	// お気に入り商品情報を削除
	public void removeLike(int userId, int itemId) throws DataAccessException;
	
	// ユーザーIDを基にお気に入りリストを取得
	public List<LikeListBean> findByUserId(int userId) throws DataAccessException;
}
