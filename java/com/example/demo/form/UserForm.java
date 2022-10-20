package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 会員情報フォーム
 * 
 * @author koto
 */
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
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
	public Boolean getBackFlg() {
		return backFlg;
	}

	public void setBackFlg(Boolean backFlg) {
		this.backFlg = backFlg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
