package com.shipping.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shipping.model.Returns;
import com.shipping.model.Shipment;

public interface ReturnsRepo extends JpaRepository<Returns, Integer>{
	@Query(value="Select * FROM Returns WHERE order_id=?1",nativeQuery=true)
	public List<Returns> findByOrderId(@Param("order_id") int order_id);

	@Query(value="SELECT * FROM Returns WHERE return_id=?1",nativeQuery=true)
	public List<Returns> findAllById(@Param("return_id") int return_id);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Returns SET current_status='returned',returned_date=?1 WHERE return_id=?2",nativeQuery=true)
	public void updateReturned(@Param("current_date") LocalDate current_date,@Param("return_id") int return_id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Returns SET current_status='recieved',recieved_date=?1 WHERE return_id=?2",nativeQuery=true)
	public void updateRecieved(@Param("current_date") LocalDate current_date,@Param("return_id") int reutrn_id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Returns SET current_status='intransit',intransit_date=?1 WHERE return_id=?2",nativeQuery=true)
	public void updateIntransit(@Param("current_date") LocalDate current_date,@Param("return_id") int return_id);
}
