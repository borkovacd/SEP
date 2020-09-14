package com.ftn.paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.paymentsystem.model.SellerData;

public interface SellerDataRepository extends JpaRepository<SellerData, Long> {

	public SellerData findOneById(long id);
	
}

