package com.example.demo.model;

import javax.persistence.Id;

import lombok.Data;

/**
 * 注文商品Model
 * 
 * @author koto
 */
@Data
public class OrderItem {
	@Id
	private Integer id;
	
	private Integer quantity;
	
	private Integer orderId;
	
	private Integer itemId;
	
	private Integer price;
}