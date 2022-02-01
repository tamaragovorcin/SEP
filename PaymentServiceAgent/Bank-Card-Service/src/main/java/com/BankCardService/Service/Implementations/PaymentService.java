package com.BankCardService.Service.Implementations;


import com.BankCardService.Dtos.*;
import com.BankCardService.Model.PaymentRequest;
import com.BankCardService.Repository.PaymentRepository;
import com.BankCardService.Service.Interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	
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
		paymentRequest.setMerchantId(paymentRequestDTO.getMerchantId());
		paymentRequest.setMerchantPassword(paymentRequestDTO.getMerchantPassword());
		paymentRequest.setMerchantOrderId(paymentRequestDTO.getMerchantOrderId());
		paymentRequest.setMerchantTimestamp(paymentRequestDTO.getMerchantTimestamp());
		paymentRequest.setSuccessUrl(paymentRequestDTO.getSuccessUrl());
		paymentRequest.setFailedUrl(paymentRequestDTO.getFailedUrl());
		paymentRequest.setErrorUrl(paymentRequestDTO.getErrorUrl());
		paymentRequest.setCallbackUrl(paymentRequestDTO.getCallbackUrl());
		PaymentRequest ret = paymentRepository.save(paymentRequest);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaymentRequest> request = new HttpEntity<PaymentRequest>(paymentRequest, headers);
		String paymentRequestId = paymentRequest.getId().toString();
		PaymentResponseDTO paymentResponseDTO = restTemplate.postForObject(  "http://localhost:8088/payment/confirm/" + paymentRequestId, request, PaymentResponseDTO.class);

		return paymentResponseDTO;
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
