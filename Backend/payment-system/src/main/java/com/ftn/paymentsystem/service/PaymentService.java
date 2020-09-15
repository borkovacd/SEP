package com.ftn.paymentsystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


@Service
public class PaymentService {
	
	static String myCI = "AQBuI2NHwzAMuKe6ZL2gXha-jCyecSecVXLIxTxH7zjoyx3j49ZhOcTWmk_58rrcIV1aAlKH_ovmQEJd";
	static String myCS = "EAFsKRFCIGcxckRJd4RLrZywJ0MnF46OO4W7MNpY7iGtcQcn7-BJS2_53-H3VgGiRU4jnUQjHoEyROXP";
	private static String CLIENT_ID = myCI;
	private static String CLIENT_SECRET = myCS;
	
	@Value("${paypal.mode}")
	private String mode;
	
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
				
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:3000/register");
		redirectUrls.setReturnUrl("http://localhost:3000/register");
		payment.setRedirectUrls(redirectUrls);
		
		payment = payment.create(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()));
		
		paymentOrder.setPaymentId(payment.getId());
		paymentOrderRepository.save(paymentOrder);
		
		return payment;
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		
		PaymentOrder paymentOrder = paymentOrderRepository.findOneByPaymentId(paymentId);
		SellerData seller = paymentOrder.getSeller();
		
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		
		payment = payment.execute(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()), paymentExecute);
		
		if(payment.getState().equals("approved")) {
			paymentOrder.setPaymentStatus(PaymentStatus.PAID);
		} else {
			paymentOrder.setPaymentStatus(PaymentStatus.FAILED);
		}
		paymentOrderRepository.save(paymentOrder);
		
		return payment;
	}

	
	private APIContext getApiContext(String clientId, String clientSecret) throws PayPalRESTException {
		
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		
		APIContext context = new APIContext(new OAuthTokenCredential
				("AQBuI2NHwzAMuKe6ZL2gXha-jCyecSecVXLIxTxH7zjoyx3j49ZhOcTWmk_58rrcIV1aAlKH_ovmQEJd", 
				 "EAFsKRFCIGcxckRJd4RLrZywJ0MnF46OO4W7MNpY7iGtcQcn7-BJS2_53-H3VgGiRU4jnUQjHoEyROXP",
				 configMap).getAccessToken());
		context.setConfigurationMap(configMap);
		return context;
	}
	
	public void canclePaymentOrder(long id) {
		PaymentOrder paymentOrder = paymentOrderRepository.findOneById(id);
		paymentOrder.setPaymentStatus(PaymentStatus.CANCELED);
		paymentOrderRepository.save(paymentOrder);
	}

	public Double getPaymentOrderPrice(String paymentOrderId) {
		return paymentOrderRepository.findOneByPaymentId(paymentOrderId).getAmount();
	}

}
