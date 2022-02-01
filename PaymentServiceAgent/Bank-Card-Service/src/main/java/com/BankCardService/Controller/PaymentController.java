package com.BankCardService.Controller;

import com.BankCardService.Dtos.AccountDTO;
import com.BankCardService.Dtos.PaymentRequestDTO;
import com.BankCardService.Dtos.PaymentResponseDTO;
import com.BankCardService.Model.PaymentRequest;
import com.BankCardService.Service.Implementations.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankcard")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;


	@PostMapping("/create")
	private ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
		System.out.println("Create payment");

		PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(paymentRequestDTO);
		return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
	}

}
