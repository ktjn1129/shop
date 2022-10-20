package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.Data;

/**
 * ユーザーModel
 * 
 * @author koto
 */
@Data
public class User {
	@Id
	private Integer id;
	
	private String email;
	
	private String password;
	
	private String name;

	private String postalCode;
	
	private String address;
	
	private String phoneNumber;
	
	private Integer authority;
	
	private Integer deleteFlag;
	
	private LocalDateTime insertDate;
	
	private String username;
}
