package com.example.demo.bean;

import lombok.Data;

/**
 * 買い物かご商品情報
 * 
 * @author koto
 */
@Data
public class BasketBean {
	
	private Integer id;
	
	private String name;
	
	private Integer price;
	
	private Integer stock;
	
	private String image;
	
	private Integer orderNum;
}