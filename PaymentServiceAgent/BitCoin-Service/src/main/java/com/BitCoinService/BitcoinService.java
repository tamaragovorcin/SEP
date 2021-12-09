package com.BitCoinService;

import com.BitCoinService.Model.BitcoinPayment;
import com.BitCoinService.Model.BitcoinPaymentDTO;
import com.BitCoinService.Model.MerchantDTO;
import com.BitCoinService.Model.ResponseBitcoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BitcoinService {

    @Autowired
    BitcoinRepository bitcoinRepository;

    public void savePayment(BitcoinPaymentDTO payment) {
        BitcoinPayment bitcoinPayment = new BitcoinPayment();
        bitcoinPayment.setTotal(payment.getTotal());
        bitcoinPayment.setCurrency(payment.getCurrency());
        bitcoinPayment.setCreateTime(new Date().toString());
        bitcoinRepository.save(bitcoinPayment);
    }

    public String createPayment(MerchantDTO merchantDTO) {
        ResponseBitcoinDTO response = createBitCoinRequest(merchantDTO);

        return getResponseAsString(merchantDTO, response);
    }

    private String getResponseAsString(MerchantDTO merchantDTO, ResponseBitcoinDTO response) {
        String paymentUrl = response.getPayment_url();
        String idTransaction = response.getId().toString();
        String uuid = response.getPayment_url().split("invoice/")[1];
        String name = merchantDTO.getMerchant_id();
        String dateTime = response.getCreated_at();
        String status = response.getStatus();
        String payer = response.getOrder_id();
        return paymentUrl + ", " + idTransaction + ", " + uuid + ", " + name + ", " + dateTime + ", " + status + ", " + payer;
    }

    private ResponseBitcoinDTO createBitCoinRequest(MerchantDTO merchantDTO) {
        Map<String, Object> mapa = new HashMap<String,Object>();
        mapa.put("order_id", UUID.randomUUID().toString());
        mapa.put("price_amount", merchantDTO.getAmount());
        mapa.put("price_currency","USD");
        mapa.put("receive_currency","USD");
        mapa.put("title", merchantDTO.getMerchant_id());
        mapa.put("description", "desc");
        mapa.put("callback_url", "https://api-sandbox.coingate.com/account/orders");
        mapa.put("success_url", "http://localhost:3001//#/bitcoinSuccess");
        mapa.put("error_url", "http://localhost:3001//#/bitcoinError");
        mapa.put("failed_url", "http://localhost:3001//#/bitcoinError");

        mapa.put("cancel_url", "http://localhost:3001//#/orders");

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Token "+ merchantDTO.getMerchant_token());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String,Object>>(mapa, headers);

        ResponseBitcoinDTO response = client.postForObject("https://api-sandbox.coingate.com/v2/orders", entity, ResponseBitcoinDTO.class);
        return response;
    }
}
