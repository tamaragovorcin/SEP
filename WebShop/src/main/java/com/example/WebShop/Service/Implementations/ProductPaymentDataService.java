package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Model.ProductPaymentData;
import com.example.WebShop.Repository.ProductPaymentDataRepository;
import com.example.WebShop.Service.IServices.IProductPaymentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPaymentDataService implements IProductPaymentDataService {

    @Autowired
    ProductPaymentDataRepository productPaymentDataRepository;


    @Override
    public ProductPaymentData findProductPaymentData() {
        return productPaymentDataRepository.findProductPaymentData();
    }

    @Override
    public String findBitcoinToken() {
        return productPaymentDataRepository.findProductPaymentData().getBitcoinToken();
    }

    @Override
    public PaypalCredentialsDTO findPaypalCredentials() {
        ProductPaymentData productPaymentData = productPaymentDataRepository.findProductPaymentData();
        return new PaypalCredentialsDTO(productPaymentData.getPaypalClientId(), productPaymentData.getPaypalClientSecret());
    }
}
