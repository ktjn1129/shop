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
	
	public void regist(OrderItem orderItem) throws DataAccessException;
	
	public List<OrderDetailBean> findByOrderId(int orderId) throws DataAccessException;
}