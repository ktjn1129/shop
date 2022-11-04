package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.FavoriteListBean;
import com.example.demo.model.Favorite;
import com.example.demo.repository.FavoriteDao;

/**
 * お気に入り商品Serviceクラス
 * 
 * @author koto
 */
@Service
public class FavoriteService {
	
	/**
	 * お気に入り商品Daoの紐付け
	 */
	@Autowired
	@Qualifier("FavoriteDaoJdbc")
	FavoriteDao dao;
	
	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param favorite お気に入り商品情報
	 */
	@Transactional
	public void addFavorite(Favorite favorite) {
		
		dao.addFavorite(favorite);
	}

	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 */
	@Transactional
	public void removeFavorite(int userId, int itemId) {
		
		dao.removeFavorite(userId, itemId);
	}
	
	/**
	 * お気に入りリスト取得メソッド
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 * @return dao.findByUserId(userId) お気に入りリスト
	 */
	public List<FavoriteListBean> findByUserId(int userId) {
		
		return dao.findByUserId(userId);
	}
}