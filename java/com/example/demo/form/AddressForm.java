package com.example.demo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * お届け先情報フォーム
 * 
 * @author koto
 */
public class AddressForm {
	
	@NotBlank
	@Size(min = 7, max = 8)
	@Pattern(regexp = "^[0-9]+$")
	private String postalCode;
	
	@NotBlank
	@Size(min = 1, max = 150)
	private String address;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String name;
	
	@NotBlank
	@Size(min = 10, max = 11)
	@Pattern(regexp = "^[0-9]+$")
	private String phoneNumber;
	

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

}
