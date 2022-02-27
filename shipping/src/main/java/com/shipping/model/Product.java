package com.shipping.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	private int product_id;
	private int return_period;
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getReturn_period() {
		return return_period;
	}
	public void setReturn_period(int return_period) {
		this.return_period = return_period;
	}
}
