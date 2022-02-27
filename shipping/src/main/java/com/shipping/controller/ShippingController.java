package com.shipping.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shipping.exception.ProductNotFoundException;
import com.shipping.exception.UniqueValidationException;
import com.shipping.model.*;
import com.shipping.repository.ProductRepo;
import com.shipping.repository.ShippingRepo;
import com.shipping.service.ReturnsService;
import com.shipping.service.ShippingService;
import com.shipping.service.UpdateService;


@RestController
public class ShippingController{
	@Autowired
	ShippingRepo repo;
	@Autowired
	ShippingService shipping_service;
	@Autowired
	ProductRepo p_repo;
	@Autowired
	ReturnsService ret;
	@PostMapping("/tracking_system/shipment")//return the object added to the database
	public Shipment createRecord(Shipment shipment) throws UniqueValidationException,ProductNotFoundException,IllegalArgumentException{
		return shipping_service.createShipment(shipment);
	}
	
	
	@GetMapping("/tracking_system/shipment")//return list of shipments present in the database
	public Optional<List<Shipment>> getRecords(@RequestParam("order_id") Optional<Integer> order_id,@RequestParam("shipment_id") Optional<Integer> shipment_id)throws IllegalArgumentException,NumberFormatException{
		return shipping_service.getShipment(order_id,shipment_id);
	}
	
	
	@PatchMapping("/tracking_system/shipment")//update status and date of happening  and return that shipment object
	public Optional<Shipment> updateRecord(@RequestParam("current_status") Status current_status,@RequestParam("shipment_id") int shipment_id) throws IllegalArgumentException,ProductNotFoundException {
		return shipping_service.updateShipment(current_status,LocalDate.now(), shipment_id);
	}
	
	
	@DeleteMapping("/tracking_system/shipment")//delete and return shipment id else return null
	public Integer cancleShipment(@RequestParam("shipment_id") int shipment_id)throws IllegalArgumentException,ProductNotFoundException{
		return shipping_service.deleteShipment(shipment_id);
	}
}
