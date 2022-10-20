package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.bean.OrderDetailBean;
import com.example.demo.model.OrderItem;

/**
 * 注文商品Dao
 * 
 * @author koto
 */
public interface OrderItemDao {
	
	// 注文商品情報を新規登録
	public void register(OrderItem orderItem) throws DataAccessException;
	
	// 注文IDを基に注文商品情報を取得
	public List<OrderDetailBean> findByOrderId(int orderId) throws DataAccessException;
}
