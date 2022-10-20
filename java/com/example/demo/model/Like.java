package com.example.demo.model;

import javax.persistence.Id;

import lombok.Data;

/**
 * お気に入り商品Model
 * 
 * @author koto
 */
@Data
public class Like {
	@Id
	private Integer id;
	
	private Integer userId;
	
	private Integer itemId;
}
