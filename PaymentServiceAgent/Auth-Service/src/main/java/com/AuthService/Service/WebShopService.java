package com.AuthService.Service;

import com.AuthService.DTO.BankPaymentMethodDTO;
import com.AuthService.DTO.PaymentMethodDTO;
import com.AuthService.DTO.UrlAddressesDTO;
import com.AuthService.DTO.WebShopDTO;
import com.AuthService.Model.WebShop;
import com.AuthService.Repository.WebShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebShopService {
    @Autowired
    WebShopRepository webShopRepository;

    public WebShop save(WebShopDTO webShopDTO) {

        WebShop webShop = new WebShop();

        webShop.setUsername(webShopDTO.getUsername());
        webShop.setPassword(webShopDTO.getPassword());
        webShop.setSuccessUrl(webShopDTO.getSuccessUrl());
        webShop.setFailureUrl(webShopDTO.getFailureUrl());
        webShop.setErrorUrl(webShopDTO.getErrorUrl());
        webShop.setEnabledBitcoin(false);
        webShop.setEnabledCard(false);
        webShop.setEnabledPayPal(false);
        webShop.setEnabledQR(false);
        webShop.setWebShopId(Integer.parseInt(webShopDTO.getWebShopId()));

        return webShopRepository.save(webShop);
    }

    public WebShop login(WebShopDTO webShopDTO) {
        WebShop webShop = webShopRepository.findByUsername(webShopDTO.getUsername());
        if(webShop!=null) {
            System.out.println(webShopDTO.getPassword());
            System.out.println(webShop.getPassword());
            if (webShopDTO.getPassword().equals(webShop.getPassword())) {
                return webShop;
            }
        }
        return null;
    }

    public void enablePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        WebShop webShop = webShopRepository.findByWebShopId(paymentMethodDTO.getWebShopId());
        String methodName = paymentMethodDTO.getMethodName();
        switch (methodName) {
            case "bitcoin":
                webShop.setEnabledBitcoin(true);
                break;
            case "paypal":
                webShop.setEnabledPayPal(true);
                break;
            case "card":
                webShop.setEnabledCard(true);
                break;
            case "qr":
                webShop.setEnabledQR(true);
                break;
        }
        webShopRepository.save(webShop);
    }
    public void enableBankPaymentMethod(BankPaymentMethodDTO paymentMethodDTO) {
        WebShop webShop = webShopRepository.findByWebShopId(paymentMethodDTO.getWebShopId());
        String methodName = paymentMethodDTO.getMethodName();
        webShop.setBank(paymentMethodDTO.getBank());
        webShop.setMerchantID(paymentMethodDTO.getMerchantID());
        webShop.setMerchantPassword(paymentMethodDTO.getMerchantPassword());
        switch (methodName) {
            case "qr":
                webShop.setEnabledQR(true);
                break;
            case "card":
                webShop.setEnabledCard(true);
                break;

        }
        System.out.println("**************************************");
        System.out.println(webShop.getMerchantPassword());
        webShopRepository.save(webShop);
    }

    public void disablePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        WebShop webShop = webShopRepository.findByWebShopId(paymentMethodDTO.getWebShopId());
        String methodName = paymentMethodDTO.getMethodName();
        switch (methodName) {
            case "bitcoin":
                webShop.setEnabledBitcoin(false);
                break;
            case "paypal":
                webShop.setEnabledPayPal(false);
                break;
            case "card":
                webShop.setEnabledCard(false);
                break;
            case "qr":
                webShop.setEnabledQR(false);
                break;
        }
        webShopRepository.save(webShop);
    }

    public PaymentMethodDTO getPaymentMethods(Integer webShopId) {
        WebShop webShop = webShopRepository.findByWebShopId(webShopId);

        List<String> enabledMethods = new ArrayList<>();
        List<String> disabledMethods = new ArrayList<>();

        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();

        if(webShop.getEnabledCard()) enabledMethods.add("card"); else {disabledMethods.add("card");}
        if(webShop.getEnabledBitcoin()) enabledMethods.add("bitcoin"); else {disabledMethods.add("bitcoin");}
        if(webShop.getEnabledQR()) enabledMethods.add("qr"); else {disabledMethods.add("qr");}
        if(webShop.getEnabledPayPal()) enabledMethods.add("paypal"); else {disabledMethods.add("paypal");}

        paymentMethodDTO.setMethods(enabledMethods);
        paymentMethodDTO.setDisabledMethods(disabledMethods);
        paymentMethodDTO.setWebShopId(webShopId);
        return paymentMethodDTO;
    }

    public UrlAddressesDTO getUrlsByWebShopId(int webShopId) {
        WebShop webShop = webShopRepository.findByWebShopId(webShopId);
        UrlAddressesDTO urlAddressesDTO = new UrlAddressesDTO();
        urlAddressesDTO.setErrorUrl(webShop.getErrorUrl());
        urlAddressesDTO.setSuccessUrl(webShop.getSuccessUrl());
        urlAddressesDTO.setFailureUrl(webShop.getFailureUrl());
        return urlAddressesDTO;
    }

    public String getmerchantID(String webShopId) {
        WebShop webShop = webShopRepository.findByWebShopId(Integer.parseInt(webShopId));
        return webShop.getMerchantID();
    }
}
