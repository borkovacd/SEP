package com.ftn.paymentsystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.paymentsystem.dto.PaymentRequestDTO;
import com.ftn.paymentsystem.enums.PaymentStatus;
import com.ftn.paymentsystem.model.PaymentOrder;
import com.ftn.paymentsystem.model.SellerData;
import com.ftn.paymentsystem.repository.PaymentOrderRepository;
import com.ftn.paymentsystem.repository.SellerDataRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


@Service
public class PaymentService {
	
	private static final String SUCCESS_URL = "/payment/complete";
	private static final String CANCEL_URL = "/payment/cancel";
	
	@Autowired
    private APIContext apiContext;
	
	
	@Autowired
	SellerDataRepository sellerDataRepository;
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	public Payment createPayment(PaymentRequestDTO paymentRequest) throws PayPalRESTException {
		
		SellerData seller = sellerDataRepository.findOneById(paymentRequest.getSellerId());
		
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setSeller(seller);
		paymentOrder.setAmount(paymentRequest.getAmount());
		paymentOrderRepository.save(paymentOrder);
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		double total = new BigDecimal(paymentRequest.getAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription("");
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("PAYPAL");
		
		Payment payment = new Payment();
		payment.setIntent("SALE");
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		
		
		String cancelUrl = "";
        String successUrl = "";
        successUrl = "https://localhost:9090/" + SUCCESS_URL + "/"+ paymentOrder.getId();
        cancelUrl = "https://localhost:9090/" + CANCEL_URL + "/" + paymentOrder.getId();
				
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		
		payment = payment.create(apiContext);
		
		paymentOrder.setPaymentId(payment.getId());
		paymentOrder.setIntent(payment.getIntent());
		paymentOrderRepository.save(paymentOrder);
		
		return payment;
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		
		PaymentOrder paymentOrder = paymentOrderRepository.findOneByPaymentId(paymentId);
		
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		
		
		payment =  payment.execute(apiContext, paymentExecute);
		
		if(payment.getState().equals("approved")) {
			paymentOrder.setPaymentStatus(PaymentStatus.PAID);
		} else {
			paymentOrder.setPaymentStatus(PaymentStatus.FAILED);
		}
		
		paymentOrder.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
		paymentOrder.setIntent(payment.getIntent());
		
		paymentOrderRepository.save(paymentOrder);
		
		return payment;
	}

	
	public void cancelPaymentOrder(long id) {
		PaymentOrder paymentOrder = paymentOrderRepository.findOneById(id);
		paymentOrder.setPaymentStatus(PaymentStatus.CANCELED);
		paymentOrderRepository.save(paymentOrder);
	}

	public Double getPaymentOrderPrice(String paymentOrderId) {
		return paymentOrderRepository.findOneByPaymentId(paymentOrderId).getAmount();
	}

}
