package com.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipping.exception.ProductNotFoundException;
import com.shipping.model.Product;
import com.shipping.model.Returns;
import com.shipping.model.Shipment;
import com.shipping.model.Status;
import com.shipping.repository.ProductRepo;
import com.shipping.repository.ReturnsRepo;
import com.shipping.repository.ShippingRepo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
@Service
public class ReturnsService {
	@Autowired
	ProductRepo p_repo;
	@Autowired
	ShippingRepo repo;
	@Autowired
	ReturnsRepo ret_repo;
	public Returns createReturn(Integer shipment_id) {
		Optional<Shipment> shipment=repo.findById(shipment_id);
		Returns returns=null;
		if(shipment.isPresent()&&isReturnable(shipment.get())) {
			returns=new Returns(shipment.get());
			System.out.println(returns);
			ret_repo.save(returns);
			repo.delete(shipment.get());
		}
		return returns;
	}
	public List<Returns> showReturn(Optional<Integer> order_id,Optional<Integer> return_id){
		if(order_id.isEmpty()&&return_id.isEmpty())
			return ret_repo.findAll();
		else if(order_id.isPresent()) {
			return ret_repo.findByOrderId(order_id.get());
		}
		else {
			return ret_repo.findAllById(return_id.get());
		}
	}
	public Optional<Returns> updateReturn(Integer return_id,Status current_status)throws IllegalArgumentException{
		if(current_status.toString().equals("returned")) {
			ret_repo.updateReturned(LocalDate.now(),return_id);
		}
		else if(current_status.toString().equals("recieved")) {
			ret_repo.updateRecieved(LocalDate.now(),return_id);
		}
		else if(current_status.toString().equals("intransit")) {
			ret_repo.updateIntransit(LocalDate.now(),return_id);
		}
		return ret_repo.findById(return_id);
	}
	public Returns deleteReturn(Integer return_id) throws ProductNotFoundException,IllegalArgumentException{
		if(!ret_repo.existsById(return_id))
			return null;
		Returns returns=ret_repo.getById(return_id);
		ret_repo.deleteById(return_id);
		return returns;
	}
	public boolean isReturnable(Shipment shipment) {
		if(!shipment.getCurrent_status().toString().equals("delivered")) {
			return false;
		}
		Product curr=p_repo.getById(shipment.getProduct_id());
		if(curr.getReturn_period()<ChronoUnit.DAYS.between(shipment.getDelivered_date(),LocalDate.now())) {
			return false;
		}
		return true;
	}
//	public Return updateReturn(int return_id,String current_status,LocalDate current_date) {
//		
//	}
}
