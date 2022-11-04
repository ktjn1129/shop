package com.example.demo.model;

import javax.persistence.Id;

import lombok.Data;

/**
 * お気に入り情報
 * 
 * @author koto
 */
@Data
public class Favorite {
	@Id
	private Integer id;
	
	private Integer userId;
	
	private Integer itemId;
}
