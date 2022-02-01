package com.example.api.controllers.bank;

import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.PaymentRequestDTO;
import com.example.api.DTOs.PaymentResponseDTO;
import com.example.api.entities.bank.PaymentRequest;
import com.example.api.services.implementations.bank.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
		System.out.println("Create payment");

		PaymentRequest paymentResponseDTO = paymentService.createPayment(paymentRequestDTO);
		return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm/{paymentRequestId}", method = RequestMethod.POST)
	private String confirmPayment(@RequestBody AccountDTO clientDTO, @PathVariable Integer paymentRequestId) {
		System.out.println("Payment request id: " + paymentRequestId);
		return paymentService.confirmPayment(clientDTO, paymentRequestId);
	}

}
