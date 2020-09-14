package com.ftn.scientificcentral.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayingRequestDTO {
	
	private long sellerId;
	private double amount;

}
