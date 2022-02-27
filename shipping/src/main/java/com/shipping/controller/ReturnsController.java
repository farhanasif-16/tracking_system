package com.shipping.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shipping.exception.ProductNotFoundException;
import com.shipping.exception.UniqueValidationException;
import com.shipping.model.Returns;
import com.shipping.model.Shipment;
import com.shipping.model.Status;
import com.shipping.repository.ReturnsRepo;
import com.shipping.repository.ShippingRepo;
import com.shipping.service.ReturnsService;

@RestController
public class ReturnsController{
	@Autowired
	ReturnsService returns_service;
	@Autowired
	ShippingRepo repo;
	@Autowired
	ReturnsRepo ret_repo;
	
	
	@PostMapping("/tracking_system/return")
	public Returns returnOrder(@RequestParam("shipment_id") int shipment_id) {
		return returns_service.createReturn(shipment_id);
	}
	
	
	@GetMapping("/tracking_system/return")
	public List<Returns> showReturns(@RequestParam("order_id") Optional<Integer> order_id,@RequestParam("return_id") Optional<Integer> return_id) {
		return returns_service.showReturn(order_id,return_id);
	}
	
	
	@PatchMapping("/tracking_system/return")
	public Optional<Returns> updateStatus(@RequestParam("return_id") int return_id,@RequestParam("current_status") Status current_status){
		return returns_service.updateReturn(return_id,current_status);
	}
	
	
	@DeleteMapping("/tracking_system/return")
	public Returns deleteReturn(@RequestParam("return_id") int return_id) {
		return returns_service.deleteReturn(return_id); 
	}
}
