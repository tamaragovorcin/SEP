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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.YearMonth;
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
	public PaymentResponseDTO getPaymentResponse(PaymentRequestDTO paymentRequestDTO) {

		PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();


		Merchant merchant = merchantService.findByMerchantId(paymentRequestDTO.getMerchantId());



		if(merchant != null){
			PaymentRequest paymentRequest = new PaymentRequest();
			paymentRequest.setAmount(paymentRequestDTO.getAmount());
			paymentRequest.setMerchantId(paymentRequestDTO.getMerchantId());
			paymentRequest.setMerchantPassword(paymentRequestDTO.getMerchantPassword());
			paymentRequest.setMerchantOrderId(1);
			paymentRequest.setMerchantTimestamp(new Date());
			paymentRequest.setSuccessUrl(paymentRequestDTO.getSuccessUrl());
			paymentRequest.setFailedUrl(paymentRequestDTO.getFailedUrl());
			paymentRequest.setErrorUrl(paymentRequestDTO.getErrorUrl());

			//if(merchant.getMerchantPassword().equals(id.getMerchantPassword())){
				if(merchant.getAccount().getBank().getName().equals("Adiko")){
					paymentRequest.setPaymentId("1");
					paymentRequest.setPaymentUrl("http://localhost:3009/#/bank1");
					paymentResponseDTO.setPaymentId("1");
					paymentResponseDTO.setPaymentUrl("http://localhost:3009/#/bank1");

				}
				else{
					paymentRequest.setPaymentId("2");
					paymentRequest.setPaymentUrl("http://localhost:3009/#/bank2");
					paymentResponseDTO.setPaymentId("2");
					paymentResponseDTO.setPaymentUrl("http://localhost:3009/#/bank2");
				}


			//}

			PaymentRequest ret = paymentRepository.save(paymentRequest);
				return paymentResponseDTO;
		}
		else {

			return null;
		}
	}
	public static void browse(String url) {
		if(Desktop.isDesktopSupported()){
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}else{
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public String confirmPayment(AccountDTO clientDTO, Integer paymentRequestId) {

		System.out.println(clientDTO.getWebshopId());

		Transaction transaction = new Transaction();
		PaymentRequest paymentRequest = getPaymentRequest(paymentRequestId);
		System.out.println(paymentRequest);
		transaction.setAmount(paymentRequest.getAmount());
		transaction.setMerchantId(paymentRequest.getMerchantId());
		transaction.setMerchantOrderId(paymentRequest.getMerchantOrderId());
		transaction.setMerchantTimestamp(paymentRequest.getMerchantTimestamp());


		Merchant m = merchantService.findByMerchantId(paymentRequest.getMerchantId());
		//UrlAddressesDTO urls = getUrlAddressByWebShopId(clientDTO.getWebshopId());


		String bankId = paymentRequestId.toString() + paymentRequestId.toString() + paymentRequestId.toString();

		if(getBankIdFromPan(clientDTO.getPAN()).equals(bankId)) {

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

			//String tempDate = client.getExpirationDate() + "/" + clientDTO.getYy();
			if (!client.getCardHolderName().equals(clientDTO.getCardHolderName()) || !client.getCardSecurityCode().equals(clientDTO.getCardSecurityCode())) {
				System.err.println("ne podudara se");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}


			/*if (paymentRequest.getAmount() > client.getAvailableFunds()) {
				System.err.println("nema sredstava");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}*/

			String merchantId = paymentRequest.getMerchantId();

			Merchant merchant = merchantService.findByMerchantId(merchantId);
			if (!merchant.getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
				System.err.println("nije dobar merchant");
				transaction.setStatus(TransactionStatus.ERROR);
				failPayment(paymentRequest);
				return paymentRequest.getErrorUrl();
			}

			client.setAvailableFunds(50.0);//client.getAvailableFunds() - paymentRequest.getAmount());
			clientService.saveNoDTO(client);


			/*merchant.setAvailableFunds(50.0);//merchant.getAvailableFunds() + paymentRequest.getAmount());
			System.out.println("aaaaaaaaaaaaaaaaa");
			merchantService.saveNoDTO(merchant);*/

			transaction.setStatus(TransactionStatus.SUCCESSFUL);
			transactionService.save(transaction);
			CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
			completePaymentResponseDTO.setStatus("PAID");

			/*ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
					new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
			System.out.println(responseEntity.getBody());*/

			return paymentRequest.getSuccessUrl();
		}
		else {
			// To nije ova banka idemo na pcc
			System.out.println("Nisu iz iste banke idemo na pcc");
			Transaction saved = transactionService.save(transaction);
			// ACQUIRER_ORDER_ID = saved.id
			// ACQUIRER_TIMESTAMP
			PccRequestDTO pccRequestDTO = new PccRequestDTO();
			pccRequestDTO.setAcquirerOrderId(saved.getId());
			pccRequestDTO.setAcquirerTimestamp(new Date());
			pccRequestDTO.setCardHolder(clientDTO.getCardHolderName());
			pccRequestDTO.setCardSecurityCode(clientDTO.getCardSecurityCode());
			pccRequestDTO.setExpirationDate(clientDTO.getExpirationDate());
			pccRequestDTO.setPanNumber(clientDTO.getPAN());
			pccRequestDTO.setAmount(paymentRequest.getAmount());
			System.err.println("posle pccRequestDTO");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PccRequestDTO> request = new HttpEntity<PccRequestDTO>(pccRequestDTO, headers);
			System.err.println("posle request");

			String merchantId = paymentRequest.getMerchantId();
			Merchant merchant = merchantService.findByMerchantId(merchantId);
			if (!merchant.getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
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

				/*merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
				clientService.saveNoDTO(merchant);*/
				transaction.setStatus(TransactionStatus.SUCCESSFUL);
				transactionService.save(transaction);
				System.err.println("posles skidanja merchantu para");

				// Obavetavamo KP o uspesnosti transakcije
				CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
				completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
				completePaymentResponseDTO.setStatus("PAID");
				System.err.println("obavestavanja kpa");

				/*ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
						new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
				System.out.println(responseEntity.getBody());*/


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

		String number = panNumber.substring(0, 3);
		return number;
	}

	private void failPayment(PaymentRequest paymentRequest) {
		System.out.println("Fail payment");
		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
		completePaymentResponseDTO.setStatus("FAILED");
		ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
				new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		System.out.println(responseEntity.getBody());
	}

	private void failPaymentPerDiem(Integer id, String url) {
		System.out.println("Fail payment");
		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(id);
		completePaymentResponseDTO.setStatus("FAILED");
	}

	public String getmerchantPANbyID(String merchantID) {
		Merchant merchant = merchantService.findByMerchantId(merchantID);
		return merchant.getAccount().getPAN();
	}

	public String payThePerDiem(PerDiemDTO clientDTO) {

		Transaction transaction = new Transaction();
		System.out.println(clientDTO.getGiroNumber());
		System.out.println(clientDTO.getCardHolderName());
		System.out.println(clientDTO.getReferenceNumber());

		for (Account account : clientService.findAll()) {
			System.out.println(account.getGiroNumber());
			System.out.println(account.getCardHolderName());
			System.out.println(account.getReferenceNumber());
			if(account.getGiroNumber().equals(clientDTO.getGiroNumber())&& account.getReferenceNumber().equals(clientDTO.getReferenceNumber()) && account.getCardHolderName().equals(clientDTO.getCardHolderName())) {
				transaction.setAmount(clientDTO.getAmount());
				transaction.setMerchantId("11111111111");
				transaction.setMerchantOrderId(1);
				transaction.setMerchantTimestamp(new Timestamp(System.currentTimeMillis()));

				transaction.setPanNumber(account.getPAN());

				//String tempDate = client.getExpirationDate() + "/" + clientDTO.getYy();



					String merchantId = "123456789";

					Merchant merchant = merchantService.findByMerchantId(merchantId);
					if (merchant == null){
						System.err.println("nije dobar merchant");
						transaction.setStatus(TransactionStatus.ERROR);
						return "error";
					}else if(merchant.getAccount().getAvailableFunds() < clientDTO.getAmount()){
						System.err.println("nema dovoljno sredstava");
						transaction.setStatus(TransactionStatus.ERROR);
						return "error";

					}
				merchant.getAccount().setAvailableFunds(merchant.getAccount().getAvailableFunds()-clientDTO.getAmount());

					merchantService.saveNoDTO(merchant);

				transaction.setStatus(TransactionStatus.SUCCESSFUL);
				transactionService.save(transaction);
				CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
				completePaymentResponseDTO.setOrder_id(1);
				completePaymentResponseDTO.setStatus("PAID");


				account.setAvailableFunds(account.getAvailableFunds() + clientDTO.getAmount());
				clientService.saveNoDTO(account);

				return "success";
			}


		}


		transaction.setStatus(TransactionStatus.FAILED);
		return "error";
	}







}