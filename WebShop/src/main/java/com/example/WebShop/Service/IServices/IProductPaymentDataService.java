package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Model.ProductPaymentData;

public interface IProductPaymentDataService {

    ProductPaymentData findProductPaymentData();
    String findBitcoinToken();
    PaypalCredentialsDTO findPaypalCredentials();
}
