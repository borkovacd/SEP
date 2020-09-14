package com.ftn.paymentsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.paymentsystem.dto.PaymentRequestDTO;
import com.ftn.paymentsystem.model.PaymentRequest;
import com.ftn.paymentsystem.service.PaymentService;
import com.ftn.paymentsystem.utils.TokenUtils;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController("/")
public class PaymentController {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("createPayment")
	public String payment(@RequestBody PaymentRequestDTO pr) {
		//return paymentService.generatePaymentUrl(payingRequest);
		
		PaymentRequest paymentRequest = tokenUtils.getPaymentRequest();
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
			return "https://localhost:8672/paypal/error.html";
	}
	
	@PostMapping("/cancel")
	public void cancelPay(@RequestBody long id) {
		paymentService.canclePaymentOrder(id);
	}

	 @GetMapping("/success")
	 public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	        try {
	            Payment payment = paymentService.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	                return "https://localhost:8672/paypal/success.html";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "https://localhost:8672/paypal/error.html";
	    }

	 @PostMapping("/paymentOrderAmount")
	 public Double getPaymentOrderPrice(@RequestBody String paymentOrderId) {
		 return paymentService.getPaymentOrderPrice(paymentOrderId);
	 }

}
