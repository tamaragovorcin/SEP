package com.BankCardService.Service.Implementations;


import com.BankCardService.Dtos.*;
import com.BankCardService.Model.PaymentRequest;
import com.BankCardService.Model.WebShop;
import com.BankCardService.Repository.PaymentRepository;
import com.BankCardService.Repository.WebShopRepository;
import com.BankCardService.Service.Interfaces.IPaymentService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private WebShopRepository webShopRepository;
	
	@Autowired
	private RestTemplate restTemplate;


	@Autowired
	private TransactionService transactionService;

	//@Value("${bankId}")
    //private String bankId;
	
	private String pccUrl = "http://localhost:6006/pcc";  // Payment Card Center

	@Override
	public PaymentRequest getPaymentRequest(Integer id) {
		Optional<PaymentRequest> paymentRequest = paymentRepository.findById(id);
		if(!paymentRequest.isPresent()) {
			return null;

		}
		return paymentRequest.get();
	}

	public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {

		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount(paymentRequestDTO.getAmount());

		WebShop webShop = webShopRepository.findByWebShopId(paymentRequestDTO.getWebshopId());
		System.out.println(webShop);
		paymentRequest.setMerchantId(webShop.getMerchantID());
		paymentRequest.setMerchantPassword(webShop.getMerchantPassword());
		paymentRequest.setMerchantOrderId(1);
		paymentRequest.setMerchantTimestamp(new Date());
		paymentRequest.setSuccessUrl(webShop.getSuccessUrl());
		paymentRequest.setFailedUrl(webShop.getFailureUrl());
		paymentRequest.setErrorUrl(webShop.getErrorUrl());
		PaymentRequest ret = paymentRepository.save(paymentRequest);

		HttpEntity<PaymentRequest> request = new HttpEntity<PaymentRequest>(paymentRequest);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8088/payment/confirm";
		ResponseEntity<PaymentResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, PaymentResponseDTO.class);
		System.out.println(response.getBody());



		return response.getBody();
	}


	
	private String getBankIdFromPan(String panNumber) {
	
		String withoutDashes = panNumber.replace("-", "");
		String number = withoutDashes.substring(1, 7);
		return number;
	}

	private void failPayment(PaymentRequest paymentRequest) {
		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
		completePaymentResponseDTO.setStatus("FAILED");
		ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
				new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		System.out.println(responseEntity.getBody());
	}
}
