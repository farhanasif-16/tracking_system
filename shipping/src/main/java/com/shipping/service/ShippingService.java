package com.shipping.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipping.exception.ProductNotFoundException;
import com.shipping.exception.UniqueValidationException;
import com.shipping.model.Product;
import com.shipping.model.Shipment;
import com.shipping.model.Status;
import com.shipping.repository.ProductRepo;
import com.shipping.repository.ShippingRepo;


@Service
public class ShippingService {
	@Autowired
	ShippingRepo shippingRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	ReturnsService returnService;
	public Shipment createShipment(int order_id,int product_id) throws ProductNotFoundException,IllegalArgumentException{
		Shipment shipment=new Shipment();
		shipment.setOrder_id(order_id);
		shipment.setProduct_id(product_id);
		if(shippingRepo.findByUnique(shipment.getOrder_id(),shipment.getProduct_id())!=null) {
			throw new UniqueValidationException("Record already found with given order_id and product_id");
		}
		if(!productRepo.existsById(shipment.getProduct_id())) {
			throw new ProductNotFoundException("ProductNotFound Exception: Product "+shipment.getProduct_id()+" not found.");
		}
		shipment.setCurrent_status(Status.ordered);
		shipment.setOrdered_date(LocalDate.now());
		return shippingRepo.save(shipment);
	}
	public Optional<List<Shipment>> getShipment(Optional<String> filter,Optional<Integer> order_id,Optional<Integer> shipment_id)throws IllegalArgumentException{
		if(filter.isPresent()) {
			return filterShipment(filter);
		}
		if(shipment_id.isEmpty()&&order_id.isEmpty()){
			return Optional.ofNullable(shippingRepo.findAll());
		}
		else if(shipment_id.isPresent()){
			return Optional.ofNullable(shippingRepo.findAllById(shipment_id.get()));
		}
		else {
			return Optional.ofNullable(shippingRepo.findByOrderId(order_id.get()));
		}
	}
	Optional<List<Shipment>> filterShipment(Optional<String> filter){
		if(filter.get().equals("shipped")) {
			return Optional.ofNullable(filterByShipped());
		}
		else if(filter.get().equals("ordered")) {
			return Optional.ofNullable(filterByOrdered());
		}
		else if(filter.get().equals("intransit")) {
			return Optional.ofNullable(filterByIntransit());
		}
		else if(filter.get().equals("delivered")) {
			return Optional.ofNullable(filterByDelivered());
		}
		return null;
	}
	public Optional<Shipment> updateShipment(Status current_status,LocalDate current_date,Integer shipment_id)throws IllegalArgumentException{
		if(current_status.toString().equals("delivered")) {
			shippingRepo.updateDelivery(current_date,shipment_id);
		}
		else if(current_status.toString().equals("shipped")) {
			shippingRepo.updateShipped(current_date,shipment_id);
		}
		else if(current_status.toString().equals("intransit")) {
			shippingRepo.updateIntransit(current_date,shipment_id);
		}
		return shippingRepo.findById(shipment_id);
	}
	public Integer deleteShipment(Integer shipment_id)throws IllegalArgumentException {
		if(isCancellable(shipment_id)) {
			shippingRepo.deleteById(shipment_id);
			return shipment_id;
		}
		else {
			return null;
		}
	}
	
	public List<Shipment> filterByShipped(){
		List<Shipment> all=shippingRepo.findAll();
		return all.stream()
				.filter(shipment->shipment.getCurrent_status().toString().equals("shipped"))
				.collect(Collectors.toList());
	}
	
	public List<Shipment> filterByIntransit(){
		List<Shipment> all=shippingRepo.findAll();
		return all.stream()
				.filter(shipment->shipment.getCurrent_status().toString().equals("intransit"))
				.collect(Collectors.toList());
	}
	
	
	public List<Shipment> filterByDelivered(){
		List<Shipment> all=shippingRepo.findAll();
		return all.stream()
				.filter(shipment->shipment.getCurrent_status().toString().equals("delivered"))
				.collect(Collectors.toList());
	}
	
	public List<Shipment> filterByOrdered(){
		List<Shipment> all=shippingRepo.findAll();
		return all.stream()
				.filter(shipment->shipment.getCurrent_status().toString().equals("ordered"))
				.collect(Collectors.toList());
	}
	
	public boolean isCancellable(int shipment_id){
		Shipment shipment=shippingRepo.getById(shipment_id);
		if(shipment.equals(null))
			return false;
		if(shipment.getCurrent_status().toString().equals("Delivered")) {
			Product curr=productRepo.getById(shipment.getProduct_id());
			if(curr.getReturn_period()<ChronoUnit.DAYS.between(shipment.getDelivered_date(),LocalDate.now())) {
				return false;
			}
		}
		return true;
	}
}
