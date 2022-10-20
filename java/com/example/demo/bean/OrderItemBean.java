package com.example.demo.bean;

import lombok.Data;

/**
 * 注文商品情報
 * 
 * @author koto
 */
@Data
public class OrderItemBean {
	
	private String name;
	
	private String image;
	
	private Integer price;
	
	private Integer quantity;
	
	private Integer subtotal;
	
	private Integer stock;
}
