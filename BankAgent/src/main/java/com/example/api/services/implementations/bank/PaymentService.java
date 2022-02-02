package com.example.api.services.implementations.bank;

import com.example.api.DTOs.*;
import com.example.api.entities.bank.*;
import com.example.api.repositories.bank.MerchantRepository;
import com.example.api.repositories.bank.PaymentRepository;
import com.example.api.services.interfaces.bank.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private AccountService clientService;


	@Autowired
	private MerchantService merchantService;

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

	@Override
	public PaymentResponseDTO getPaymentResponse(PaymentRequestDTO id) {

		PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();


		Merchant merchant = merchantService.findByMerchantId(id.getMerchantId());


		if(merchant != null){
			//if(merchant.getMerchantPassword().equals(id.getMerchantPassword())){
				if(merchant.getAccount().getBank().getName().equals("Adiko")){
					paymentResponseDTO.setPaymentId("1");
					paymentResponseDTO.setPaymentUrl("http://localhost:3009/#/bank1");
				}
				else{
					paymentResponseDTO.setPaymentId("2");
					paymentResponseDTO.setPaymentUrl("http://localhost:3009/#/bank2");
				}


				return paymentResponseDTO;
			//}

		}
		else {

			return null;
		}
	}

	@Override
	public String confirmPayment(AccountDTO clientDTO, Integer paymentRequestId) {
		// Proveravamo da li je to klijent ove banke
		//System.err.println("bankId " + bankId);
		System.err.println("skraceni pan: " + getBankIdFromPan(clientDTO.getPAN()));

		Transaction transaction = new Transaction();
		PaymentRequest paymentRequest = getPaymentRequest(paymentRequestId);

		transaction.setAmount(paymentRequest.getAmount());
		transaction.setMerchantId(paymentRequest.getMerchantId());
		transaction.setMerchantOrderId(paymentRequest.getMerchantOrderId());
		transaction.setMerchantTimestamp(paymentRequest.getMerchantTimestamp());


		if(getBankIdFromPan(clientDTO.getPAN()).contentEquals("bankId")) {

			// To je ova banka
			System.out.println("Iz iste su banke");

			Optional<Account> clientOpt = clientService.getClient(clientDTO.getPAN());
			if(!clientOpt.isPresent()) {
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}

			Account client = clientOpt.get();
			transaction.setPanNumber(client.getPAN());
			String tempDate = clientDTO.getMm() + "/" + clientDTO.getYy();
			if (!client.getCardHolderName().equals(clientDTO.getCardHolderName()) || !client.getCardSecurityCode().equals(clientDTO.getCardSecurityCode())
					|| !client.getExpirationDate().equals(tempDate)) {
				System.err.println("ne podudara se");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}

			if (paymentRequest.getAmount() > client.getAvailableFunds()) {
				System.err.println("nema sredstava");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}

			String merchantId = paymentRequest.getMerchantId();
			Account merchant = clientService.getClientByMerchantId(merchantId);
			if (!merchant.getMerchant().getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
				System.err.println("nije dobar merchant");
				transaction.setStatus(TransactionStatus.ERROR);
				failPayment(paymentRequest);
				return paymentRequest.getErrorUrl();
			}

			client.setAvailableFunds(client.getAvailableFunds() - paymentRequest.getAmount());
			clientService.saveNoDTO(client);
			merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
			clientService.saveNoDTO(merchant);
			transaction.setStatus(TransactionStatus.SUCCESSFUL);
			transactionService.save(transaction);

			CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
			completePaymentResponseDTO.setStatus("PAID");

			ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
					new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
			System.out.println(responseEntity.getBody());

			return paymentRequest.getSuccessUrl();
		}
		else {
			// To nije ova banka idemo na pcc
			System.out.println("Nisu iz iste banke idemo na pcc");
			Transaction saved = transactionService.save(transaction);
			System.err.println("posle cuvanja transakcije");
			// ACQUIRER_ORDER_ID = saved.id
			// ACQUIRER_TIMESTAMP
			PccRequestDTO pccRequestDTO = new PccRequestDTO();
			pccRequestDTO.setAcquirerOrderId(saved.getId());
			pccRequestDTO.setAcquirerTimestamp(new Date());
			pccRequestDTO.setCardHolder(clientDTO.getCardHolderName());
			pccRequestDTO.setCvv(Integer.parseInt(clientDTO.getCardSecurityCode()));
			pccRequestDTO.setMm(clientDTO.getMm());
			pccRequestDTO.setPanNumber(clientDTO.getPAN());
			pccRequestDTO.setYy(clientDTO.getYy());
			pccRequestDTO.setAmount(paymentRequest.getAmount());
			System.err.println("posle pccRequestDTO");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PccRequestDTO> request = new HttpEntity<PccRequestDTO>(pccRequestDTO, headers);
			System.err.println("posle request");

			String merchantId = paymentRequest.getMerchantId();
			Account merchant = clientService.getClientByMerchantId(merchantId);
			if (!merchant.getMerchant().getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
				System.err.println("nije dobar merchant");
				transaction.setStatus(TransactionStatus.ERROR);
				failPayment(paymentRequest);
				return paymentRequest.getErrorUrl();
			} // Ako ne potoji prodavac nece se ni otici do kupca


			// Ovde dobijamo podatke o uspesnosti transakcije
			AcquirerResponseDTO response = restTemplate.postForObject(pccUrl + "/payRedirect", request, AcquirerResponseDTO.class);

			System.err.println("posle response " + response);

			if(response.getIsAuthentificated() && response.getIsTransactionAutorized()) {
				System.err.println(" usao u if ");

				merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
				clientService.saveNoDTO(merchant);
				transaction.setStatus(TransactionStatus.SUCCESSFUL);
				transactionService.save(transaction);
				System.err.println("posles skidanja merchantu para");

				// Obavetavamo KP o uspesnosti transakcije
				CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
				completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
				completePaymentResponseDTO.setStatus("PAID");
				System.err.println("obavestavanja kpa");

				ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
						new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
				System.out.println(responseEntity.getBody());

				return paymentRequest.getSuccessUrl();
			}

			// TODO: failed i error url

			if(!response.getIsAuthentificated()) {
				System.err.println("error");
				return paymentRequest.getErrorUrl();

			}
			if(!response.getIsTransactionAutorized()) {
				System.err.println("fail");
				return paymentRequest.getFailedUrl();
			}

			return paymentRequest.getErrorUrl();
		}



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