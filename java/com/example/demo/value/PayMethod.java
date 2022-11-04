package com.example.demo.value;

/**
 * 支払方法選択用定数
 * 
 * @author koto
 */
public enum PayMethod {
	
	CREDIT_CARD(1, "クレジットカード"),
	BANK_TRANSFER(2, "銀行振込"),
	CASH_ON_DELIVERY(3, "代金引換え"),
	ELECTRONIC_MONEY(4, "電子マネー"),
	CONVENIENCE_STORE(5, "コンビニ決済");
	
	private final int value;
	private final String name;
	
	private PayMethod(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
