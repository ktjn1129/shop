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
	
	public Order findById(int id) throws DataAccessException;
	
	public Order findByInsertDate(LocalDateTime date) throws DataAccessException;
	
	public List<Order> findByUserId(int userId) throws DataAccessException;
	
	public Integer regist(Order order) throws DataAccessException;
}
