package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.Data;

/**
 * 注文Model
 * 
 * @author koto
 */
@Data
public class Order {
	@Id
	private Integer id;
	
	private String postalCode;
	
	private String address;
	
	private String name;
	
	private String phoneNumber;
	
	private Integer payMethod;
	
	private Integer userId;
	
	private LocalDateTime insertDate;
}
