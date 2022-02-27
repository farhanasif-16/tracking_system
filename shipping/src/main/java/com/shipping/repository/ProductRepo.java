package com.shipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shipping.model.Product;

public interface ProductRepo extends JpaRepository<Product,Integer>{
	
}
