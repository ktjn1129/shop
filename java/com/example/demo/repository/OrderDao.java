package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.Order;

/**
 * 注文Dao
 * 
 * @author koto
 */
public interface OrderDao {
	
	// 注文IDを基に注文情報を取得
	public Order findById(int id) throws DataAccessException;
	
	// 注文日時を基に注文情報を取得
	public Order findByInsertDate(LocalDateTime date) throws DataAccessException;
	
	// ユーザーIDを基に注文情報を取得
	public List<Order> findByUserId(int userId) throws DataAccessException;
	
	// 注文情報を新規登録
	public Integer register(Order order) throws DataAccessException;
}
