package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.bean.FavoriteListBean;
import com.example.demo.model.Favorite;

/**
 * お気に入り商品Dao
 * 
 * @author koto
 */
public interface FavoriteDao {
	
	public void addFavorite(Favorite favorite) throws DataAccessException;
	
	public void removeFavorite(int userId, int itemId) throws DataAccessException;
	
	public List<FavoriteListBean> findByUserId(int userId) throws DataAccessException;
}
