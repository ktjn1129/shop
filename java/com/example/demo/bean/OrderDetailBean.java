package com.example.demo.bean;

import javax.persistence.Id;

import lombok.Data;

/**
 * 注文詳細情報
 * 
 * @author koto
 */
@Data
public class OrderDetailBean {
	@Id
	private Integer id;
	
	private Integer quantity;
	
	private Integer orderId;
	
	private Integer itemId;
	
	private Integer price;
	
	private String name;
}
