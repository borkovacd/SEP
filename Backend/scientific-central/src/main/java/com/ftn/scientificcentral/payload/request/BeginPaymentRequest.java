package com.ftn.scientificcentral.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeginPaymentRequest {
	
	@NotBlank
	private double magazine;

	@NotBlank
	private String paymentType;

	   

}
