package com.example.api.services.implementations.bank;

import com.example.api.DTOs.PccRequestDTO;
import com.example.api.DTOs.PccResponseDTO;
import com.example.api.entities.bank.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Optional;

@Service
public class PccService {

	@Autowired 
	private AccountService clientService;
	
	public PccResponseDTO pay(PccRequestDTO pccRequestDTO) {
		
		Optional<Account> clientOpt = clientService.getClient(pccRequestDTO.getPanNumber());
		if(!clientOpt.isPresent()) {
			return failAuthentification(pccRequestDTO); // Ako klijent sa tim PAN-om ne postoji u ovoj banci
		}

		Account client = clientOpt.get();
		//String tempDate = pccRequestDTO.getMm() + "/" + pccRequestDTO.getYy();
		if (!client.getCardHolderName().equals(pccRequestDTO.getCardHolder()) || !client.getCardSecurityCode().equals(pccRequestDTO.getCvv())
				|| !client.getExpirationDate().equals(YearMonth.parse(pccRequestDTO.getExpirationDate()))) {
			System.err.println("PccService: Podaci se ne podudaraju. ");
			return failAuthentification(pccRequestDTO);
		}

		if (pccRequestDTO.getAmount() > client.getAvailableFunds()) {
			System.err.println("nema sredstava");
			return failPayment(pccRequestDTO);
		}

		client.setAvailableFunds(client.getAvailableFunds() - pccRequestDTO.getAmount());
		clientService.saveNoDTO(client);
		
		return successPayment(pccRequestDTO);
	}

	private PccResponseDTO successPayment(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(true);
		ret.setIsTransactionAutorized(true);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}

	private PccResponseDTO failAuthentification(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(false);
		ret.setIsTransactionAutorized(false);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}
	
	private PccResponseDTO failPayment(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(true);
		ret.setIsTransactionAutorized(false);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}

}
