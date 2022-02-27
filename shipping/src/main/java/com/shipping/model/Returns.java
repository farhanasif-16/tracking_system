package com.shipping.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = 
//other constraints
@UniqueConstraint(name = "UniqueOrder", columnNames = { "order_id", "product_id" }))
public class Returns {
	public Returns() {}
	public Returns(Shipment shipment){
		this.product_id=shipment.getProduct_id();
		this.order_id=shipment.getOrder_id();
		this.returned_date=LocalDate.now();
		this.current_status=Status.returned;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int return_id;
	private int product_id;
	private int order_id;
	@Enumerated(EnumType.STRING)
	private Status current_status;
	@Transient
	private LocalDate current_date;
	@Column(name = "returned_date", columnDefinition = "DATE")
	private LocalDate returned_date;
	public int getReturn_id() {
		return return_id;
	}
	public void setReturn_id(int return_id) {
		this.return_id = return_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Status getCurrent_status() {
		return current_status;
	}
	public void setCurrent_status(Status current_status) {
		this.current_status = current_status;
	}
	public LocalDate getCurrent_date() {
		return current_date;
	}
	public void setCurrent_date(LocalDate current_date) {
		this.current_date = current_date;
	}
	public LocalDate getReturned_date() {
		return returned_date;
	}
	public void setReturned_date(LocalDate returned_date) {
		this.returned_date = returned_date;
	}
	public LocalDate getIntransit_date() {
		return intransit_date;
	}
	public void setIntransit_date(LocalDate intransit_date) {
		this.intransit_date = intransit_date;
	}
	public LocalDate getRecieved_date() {
		return recieved_date;
	}
	public void setRecieved_date(LocalDate recieved_date) {
		this.recieved_date = recieved_date;
	}
	@Column(name = "intransit_date", columnDefinition = "DATE")
	private LocalDate intransit_date;
	@Column(name = "recieved_date", columnDefinition = "DATE")
	private LocalDate recieved_date;
}

