package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.Data;

/**
 * カテゴリーModel
 * 
 * @author koto
 */
@Data
public class Category {
	@Id
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean deleteFlag;
	
	private LocalDateTime insertDate;
}
