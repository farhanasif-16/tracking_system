package com.shipping.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.shipping.model.Status;
@Entity
@Table(uniqueConstraints = 
//other constraints
@UniqueConstraint(name = "UniqueOrder", columnNames = { "order_id", "product_id" }))
public class Shipment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int shipment_id;
	//if  we have product_id then tracking_id is relevant else order id can be @Id
	private int order_id;
	//optional
	private int product_id;
	@Enumerated(EnumType.STRING)
	//do we need to add product_id as a column too coz different product may have different status
	private Status current_status;
	@Column(name="ordered_date",columnDefinition="DATE")
	private LocalDate ordered_date;
	@Column(name = "shipped_date", columnDefinition = "DATE")
	private LocalDate shipped_date;
	@Column(name = "intransit_date", columnDefinition = "DATE")
	private LocalDate intransit_date;
	@Column(name = "delivered_date", columnDefinition = "DATE")
	private LocalDate delivered_date;
	@Transient
	private LocalDate date;
	
	
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public LocalDate getOrdered_date() {
		return ordered_date;
	}
	public void setOrdered_date(LocalDate ordered_date) {
		this.ordered_date = ordered_date;
	}
	public int getShipment_id() {
		return shipment_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setShipment_id(int shipment_id) {
		this.shipment_id = shipment_id;
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
		this.current_status=current_status;
	}
	public LocalDate getShipped_date() {
		return shipped_date;
	}
	public void setShipped_date(LocalDate shipped_date) {
			this.shipped_date = shipped_date;
	}
	public void setIntransit_date(LocalDate intransit_date) {
			this.intransit_date = intransit_date;
	}
	public LocalDate getIntransit_date() {
		return intransit_date;
	}
	public LocalDate getDelivered_date() {
		return delivered_date;
	}
	public void setDelivered_date(LocalDate delivered_date) {
			this.delivered_date = delivered_date;
	}
}

