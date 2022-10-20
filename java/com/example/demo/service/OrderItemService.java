package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.OrderDetailBean;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderItemDao;

/**
 * 注文商品Serviceクラス
 * 
 * @author koto
 */
@Transactional
@Service
public class OrderItemService {
	
	/**
	 * 商品Daoの紐付け
	 */
	@Autowired
	@Qualifier("OrderItemDaoJdbc")
	OrderItemDao dao;
	
	/**
	 * 注文商品登録メソッド
	 * 
	 * @param orderItem 注文商品情報
	 */
	public void register(OrderItem orderItem) {
		
		dao.register(orderItem);
	}
	
	/**
	 * 注文商品情報取得メソッド
	 * 
	 * @param orderId 注文ID
	 * @return dao.findByOrderId(orderId) 注文商品情報
	 */
	public List<OrderDetailBean> findByOrderId(int orderId) {
		
		return dao.findByOrderId(orderId);
	}
}
