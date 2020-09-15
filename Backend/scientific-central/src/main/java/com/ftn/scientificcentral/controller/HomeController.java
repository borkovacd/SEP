package com.ftn.scientificcentral.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.scientificcentral.dto.PayingRequestDTO;
import com.ftn.scientificcentral.payload.request.BeginPaymentRequest;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payment")
public class HomeController {
	
	@Autowired
	RestTemplate restTemplate;
    
	@PostMapping("/beginPaymentProcess")
	public String beginPaymentProcess(@Valid @RequestBody BeginPaymentRequest request) {
		
		PayingRequestDTO payingRequest = new PayingRequestDTO(1, request.getMagazine());
		ResponseEntity<String> response = restTemplate.postForEntity("https://localhost:9090/createPayment", payingRequest, String.class);
		
		return response.getBody();
	}

}