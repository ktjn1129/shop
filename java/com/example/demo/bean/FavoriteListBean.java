package com.example.demo.bean;

import lombok.Data;

/**
 * お気に入りリスト情報
 * 
 * @author koto
 */
@Data
public class FavoriteListBean {
	
	private Integer itemId;
	
	private String name;
	
	private Integer price;
	
	private String description;
	
	private String image;
}