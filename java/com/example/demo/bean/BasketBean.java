package com.example.demo.bean;

import javax.persistence.Id;

import lombok.Data;

/**
 * 買い物かご商品情報
 * 
 * @author koto
 */
@Data
public class BasketBean {
	@Id
	private Integer id;
	
	private String name;
	
	private Integer price;
	
	private Integer stock;
	
	private String image;
	
	private Integer orderNum;
}