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
	
	
	@PostMapping("createPayment")
	public String createPayment(@RequestBody PaymentRequestDTO pr) {
		
		System.out.println("Usao u createPayment metodu!");
		
		try {
			Payment payment = paymentService.createPayment(pr);
			
			for(Links link:payment.getLinks()) {
				System.out.println("LINK: " + link.getRel());
				System.out.println("LINK: " + link.getHref());
				if(link.getRel().equals("approval_url")) {	
					return link.getHref();
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	@GetMapping(value = "/payment/complete/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
		System.out.println("Usao u completePay metodu!");
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                //RestTemplate restTemplate = new RestTemplate();
                //ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8080/api/payment/complete/"+id, String.class);
                return new RedirectView("http://localhost:3000/payment/success");
            }
        } catch (PayPalRESTException e) {
            System.out.print(e);
        }
        return new RedirectView("http://localhost:3000");

    }

    @GetMapping(value = "/payment/cancel/{id}")
    public RedirectView cancelPay(@PathVariable String id) {
    	
    	System.out.println("Usao u cancelPay metodu!");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new RedirectView("http://localhost:3000/payment/error");

    }
    
	/*@PostMapping("/cancel")
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
	 }*/

}
