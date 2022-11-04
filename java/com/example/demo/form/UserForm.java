package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 会員情報フォーム
 * 
 * @author koto
 */
@Data
public class UserForm {	
	
	private Integer id;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 8, max = 16)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String name;
	
	@NotBlank
	@Size(min = 7, max = 8)
	@Pattern(regexp = "^[0-9]+$")
	private String postalCode;
	
	@NotBlank
	@Size(min = 1, max = 150)
	private String address;
	
	@NotBlank
	@Size(min = 10, max = 11)
	@Pattern(regexp = "^[0-9]+$")
	private String phoneNumber;
	
	private Integer authority;
	
	private Integer deleteFlag;
	
	private String insertDate;
	
	private Boolean backFlg;
	
	private String username;
}
