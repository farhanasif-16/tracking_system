package com.shipping.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.shipping.model.Shipment;
import com.shipping.model.Status;
import com.shipping.repository.ShippingRepo;
@Service
public class UpdateService {
	@Autowired
	ShippingRepo repo;
	public Optional<Shipment> update(Status current_status,LocalDate current_date,int id) { 
		if(current_status.toString().equals("delivered")) {
			repo.updateDelivery(current_date,id);
		}
		else if(current_status.toString().equals("shipped")) {
			repo.updateShipped(current_date,id);
		}
		else if(current_status.toString().equals("intransit")) {
			repo.updateIntransit(current_date,id);
		}
		return repo.findById(id);
	}
}
