package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.Data;

/**
 * 商品Model
 * 
 * @author koto
 */
@Data
public class Item {
	@Id
	private Integer id;
	
	private String name;
	
	private Integer price;
	
	private String description;
	
	private Integer stock;
	
	private String image;
	
	private Integer categoryId;
	
	private Integer deleteFlag;
	
	private LocalDateTime insertDate;
}
