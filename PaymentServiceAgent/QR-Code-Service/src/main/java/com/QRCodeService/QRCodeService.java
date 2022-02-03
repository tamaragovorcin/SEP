package com.QRCodeService;

import com.QRCodeService.DTO.IdDTO;
import com.QRCodeService.FeignClients.AuthFeignClient;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class QRCodeService {

    @Autowired
    AuthFeignClient authFeignClient;

    public String getMerchantPAN(String webShopId) {
        String merchantID =  authFeignClient.getMerchantID(webShopId);
        String url = "http://localhost:8088/payment/merchantPAN";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        IdDTO idDTO = new IdDTO();
        idDTO.setMerchantID(merchantID);
        HttpEntity<IdDTO> entity = new HttpEntity<>(idDTO);

        ResponseEntity<String> response =restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
