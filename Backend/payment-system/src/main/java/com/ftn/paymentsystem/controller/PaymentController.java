package com.ftn.paymentsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.paymentsystem.dto.PaymentRequestDTO;
import com.ftn.paymentsystem.service.PaymentService;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController("/")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	private static final String SUCCESS_URL = "http://localhost:3000/payment/success";
	private static final String CANCEL_URL = "http://localhost:3000/payment/cancel";
	private static final String ERROR_URL = "http://localhost:3000/payment/error";
	
	
	@PostMapping("createPayment")
	public String createPayment(@RequestBody PaymentRequestDTO pr) {
		
		try {
			Payment payment = paymentService.createPayment(pr);
			
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {	
					return link.getHref();
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return ERROR_URL;
	}
	
	
	@GetMapping(value = "/payment/complete/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                return new RedirectView(SUCCESS_URL);
            }
        } catch (PayPalRESTException e) {
            System.out.print(e);
        }
        return new RedirectView(ERROR_URL);

    }

    @GetMapping(value = "/payment/cancel/{id}")
    public RedirectView cancelPayment(@PathVariable Long id) {
    	
    	paymentService.cancelPaymentOrder(id);
    	
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new RedirectView(CANCEL_URL);
    }

}
