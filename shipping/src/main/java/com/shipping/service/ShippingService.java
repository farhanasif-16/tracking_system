package com.shipping.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipping.exception.ProductNotFoundException;
import com.shipping.exception.UniqueValidationException;
import com.shipping.model.Filter;
import com.shipping.model.Product;
import com.shipping.model.Shipment;
import com.shipping.model.Status;
import com.shipping.repository.ProductRepo;
import com.shipping.repository.ShippingRepo;

@Service
public class ShippingService {
	@Autowired
	ShippingRepo repo;
	@Autowired
	ProductRepo p_repo;
	@Autowired
	ReturnsService ret;
	public Shipment createShipment(Shipment shipment) throws ProductNotFoundException,IllegalArgumentException{
		if(repo.findByUnique(shipment.getOrder_id(),shipment.getProduct_id())!=null) {
			throw new UniqueValidationException("Record already found with given order_id and product_id");
		}
		if(!p_repo.existsById(shipment.getProduct_id())) {
			throw new ProductNotFoundException("ProductNotFound Exception: Product "+shipment.getProduct_id()+" not found.");
		}
		shipment.setCurrent_status(Status.ordered);
		shipment.setOrdered_date(LocalDate.now());
		return repo.save(shipment);
	}
	public Optional<List<Shipment>> getShipment(Optional<Integer> order_id,Optional<Integer> shipment_id)throws IllegalArgumentException{

		if(shipment_id.isEmpty() && order_id.isEmpty()){
			return Optional.ofNullable(repo.findAll());
		}
		else if(shipment_id.isPresent()){
			return Optional.ofNullable(repo.findAllById(shipment_id.get()));
		}
		else {
			return Optional.ofNullable(repo.findByOrderId(order_id.get()));
		}
	}
	public Optional<Shipment> updateShipment(Status current_status,LocalDate current_date,Integer shipment_id)throws IllegalArgumentException{
		if(current_status.toString().equals("delivered")) {
			repo.updateDelivery(current_date,shipment_id);
		}
		else if(current_status.toString().equals("shipped")) {
			repo.updateShipped(current_date,shipment_id);
		}
		else if(current_status.toString().equals("intransit")) {
			repo.updateIntransit(current_date,shipment_id);
		}
		return repo.findById(shipment_id);
	}
	public Integer deleteShipment(Integer shipment_id)throws IllegalArgumentException {
		if(isCancellable(shipment_id)) {
			repo.deleteById(shipment_id);
			return shipment_id;
		}
		else {
			return null;
		}
	}
	
	
	
	public boolean isCancellable(int shipment_id)throws P{
		Shipment shipment=repo.getById(shipment_id);
		if(shipment.equals(null))
			return false;
		if(shipment.getCurrent_status().toString().equals("Delivered")) {
			Product curr=p_repo.getById(shipment.getProduct_id());
			if(curr.getReturn_period()<ChronoUnit.DAYS.between(shipment.getDelivered_date(),LocalDate.now())) {
				return false;
			}
		}
		return true;
	}
}
