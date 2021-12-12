package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Model.Conferences.ConferencesPaymentData;

public interface IConferencePaymentDataService {

    ConferencesPaymentData findConferencesPaymentData();
    String findBitcoinToken();
    PaypalCredentialsDTO findPaypalCredentials();
}
