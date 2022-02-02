package com.example.api.controllers.bank;

import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.PaymentRequestDTO;
import com.example.api.DTOs.PaymentResponseDTO;
import com.example.api.entities.bank.PaymentRequest;
import com.example.api.services.implementations.bank.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/confirm")
	@CrossOrigin
	public PaymentResponseDTO confirm(@RequestBody PaymentRequestDTO clientDTO) {
		System.out.println("Payment request uslaaaaaaaaaaaa " );
		return paymentService.getPaymentResponse(clientDTO);
	}

	@PostMapping("/confirm/{paymentRequestId}")
	public String confirmPayment(@RequestBody AccountDTO clientDTO, @PathVariable Integer paymentRequestId) {
		System.out.println("Payment request id: " + paymentRequestId);
		return paymentService.confirmPayment(clientDTO, paymentRequestId);
	}
}
