package com.shipping.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shipping.model.*;

import reactor.core.publisher.Flux;
@Repository
public interface ShippingRepo extends JpaRepository<Shipment, Integer>{
	@Modifying
	@Transactional
	@Query("UPDATE Shipment SET current_status='delivered',delivered_date=?1 WHERE shipment_id=?2")
	public void updateDelivery(@Param("current_date") LocalDate date,@Param("shipment_id") int id);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Shipment SET current_status='intransit', intransit_date=?1 WHERE shipment_id=?2")
	public void updateIntransit(@Param("current_date") LocalDate date,@Param("shipment_id") int id);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Shipment SET current_status='shipped', shipped_date=?1 WHERE shipment_id=?2")
	public void updateShipped(@Param("current_date") LocalDate date,@Param("shipment_id") int id);
	
	
	@Query(value="Select * FROM Shipment WHERE order_id=?1",nativeQuery=true)
	public List<Shipment> findByOrderId(@Param("order_id") int id);
	
	
	@Query(value="SELECT * FROM Shipment WHERE order_id=?1 AND product_id=?2",nativeQuery=true)
	public Shipment findByUnique(@Param("order_id") int order_id,@Param("product_id") int product_id);
	
	@Query(value="SELECT * FROM Shipment WHERE shipment_id=?1",nativeQuery=true)
	public List<Shipment> findAllById(@Param("shipment_id") int shipment_id);
	
	@Query(value="SELECT * FROM Shipment WHERE current_status='shipped'",nativeQuery=true)
	public List<Shipment> filterByShipped();
}
