package com.example.api.controllers.bank;

import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.IdDTO;
import com.example.api.DTOs.PaymentRequestDTO;
import com.example.api.DTOs.PaymentResponseDTO;
import com.example.api.entities.bank.PaymentRequest;
import com.example.api.services.implementations.bank.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping("/confirm")
	@CrossOrigin
	public PaymentResponseDTO confirm(@RequestBody PaymentRequestDTO clientDTO) {
try {

	logger.info("Confirming payment request");
	return paymentService.getPaymentResponse(clientDTO);

}catch(Exception e){

	logger.error("Error while confirming payment request");
	return null;
}
	}

	@PostMapping("/confirm/{paymentRequestId}")
	@CrossOrigin
	public String confirmPayment(@RequestBody AccountDTO clientDTO, @PathVariable Integer paymentRequestId) {
		String url = paymentService.confirmPayment(clientDTO, paymentRequestId);
		paymentService.browse(url);

		return "Ok";
	}

	@PostMapping("/merchantPAN")
	@CrossOrigin
	public String getPan(@RequestBody IdDTO idDTO) {
		return paymentService.getmerchantPANbyID(idDTO.getMerchantID());
	}


}
