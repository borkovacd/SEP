package com.ftn.scientificcentral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.scientificcentral.dto.PayingRequestDTO;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	RestTemplate restTemplate;
    
	@PostMapping("/beginPayingProcess")
	public String beginPayingProcess(@RequestBody double amount) {
		
		PayingRequestDTO payingRequest = new PayingRequestDTO(1, amount);
		ResponseEntity<String> response = restTemplate.postForEntity("https://localhost:9090/createPayment", payingRequest, String.class);
		
		return response.getBody();
	}

}