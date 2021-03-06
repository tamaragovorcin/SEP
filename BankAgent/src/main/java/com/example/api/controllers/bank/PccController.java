package com.example.api.controllers.bank;

import com.example.api.DTOs.PccRequestDTO;
import com.example.api.services.implementations.bank.PccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pcc")
public class PccController {
	
	@Autowired
	private PccService pccService;

	private static final Logger logger = LoggerFactory.getLogger(PccController.class);
	// Endpoint koji se gadja iz PCC-a. Ovaj endpoint treba da proveri da li su podaci validni i da u zavisnosti od toga izvrsi 
	// ili ne izvrsi transakciju
	@PostMapping("/pay")
	@CrossOrigin
	public ResponseEntity<?> pay(@RequestBody PccRequestDTO pccRequestDTO) {

		return new ResponseEntity<>(pccService.pay(pccRequestDTO), HttpStatus.OK);
	}
}
