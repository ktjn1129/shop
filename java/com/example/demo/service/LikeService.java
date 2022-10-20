package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.LikeListBean;
import com.example.demo.model.Like;
import com.example.demo.repository.LikeDao;

/**
 * お気に入り商品Serviceクラス
 * 
 * @author koto
 */
@Transactional
@Service
public class LikeService {
	
	/**
	 * お気に入り商品Daoの紐付け
	 */
	@Autowired
	@Qualifier("LikeDaoJdbc")
	LikeDao dao;
	
	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param like お気に入り商品情報
	 */
	public void addLike(Like like) {
		
		dao.addLike(like);
	}

	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 */
	public void removeLike(int userId, int itemId) {
		
		dao.removeLike(userId, itemId);
	}
	
	/**
	 * お気に入りリスト取得メソッド
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 * @return dao.findByUserId(userId) お気に入りリスト
	 */
	public List<LikeListBean> findByUserId(int userId) {
		
		return dao.findByUserId(userId);
	}
}
