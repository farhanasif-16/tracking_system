package com.shipping.exception;

public class ProductNotFoundException extends RuntimeException{
	private String meassage;

	public ProductNotFoundException(String meassage) {
		super();
		this.meassage = meassage;
	}

	public String getMeassage() {
		return meassage;
	}

	public void setMeassage(String meassage) {
		this.meassage = meassage;
	}
}
