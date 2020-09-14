package com.ftn.paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.paymentsystem.model.PaymentOrder;


public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
	
	PaymentOrder findOneById(long id);
	
	PaymentOrder findOneByPaymentId(String paymentId);

}