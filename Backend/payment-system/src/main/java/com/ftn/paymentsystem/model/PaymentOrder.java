package com.ftn.paymentsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ftn.paymentsystem.enums.PaymentStatus;


import lombok.Data;

@Data
@Entity
public class PaymentOrder {
	
	@Id
	@GeneratedValue()
	private long id;
	
	private String paymentId;
	
	@ManyToOne
	private SellerData seller;
	
	private String payerId;
	
	private double amount;
	private String method;
	private String intent;
	private String description;
	
	//inicijalno status je 'CREATED'
	private PaymentStatus paymentStatus = PaymentStatus.CREATED;

}
