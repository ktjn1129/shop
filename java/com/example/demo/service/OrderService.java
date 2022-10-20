package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderDao;

/**
 * 注文Serviceクラス
 * 
 * @author koto
 */
@Transactional
@Service
public class OrderService {
	
	/**
	 * 注文Daoの紐付け
	 */
	@Autowired
	@Qualifier("OrderDaoJdbc")
	OrderDao dao;
	
	/**
	 * 注文ID検索メソッド
	 * 
	 * @param id 商品ID
	 * @return dao.findById(id) 注文情報
	 */
	public Order findById(int id) {
		
		return dao.findById(id);
	}
	
	/**
	 * 注文日時検索メソッド
	 * 
	 * @param id 商品ID
	 * @return dao.findById(id) 注文情報
	 */
	public Order findByInsertDate(LocalDateTime date) {
		
		return dao.findByInsertDate(date);
	}
	
	/**
	 * ユーザーID検索メソッド
	 * 
	 * @param userId ユーザーID
	 * @return dao.findByUserId(userId) 注文情報
	 */
	public List<Order> findByUserId(int userId) {
		
		return dao.findByUserId(userId);
	}
	
	/**
	 * 注文登録メソッド
	 * 
	 * @param order 注文情報
	 */
	public Integer register(Order order) {
		
		return dao.register(order);
	}
}