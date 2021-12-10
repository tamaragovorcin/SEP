package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Model.Conferences.ConferencesPaymentData;
import com.example.WebShop.Repository.Conferences.ConferencesPaymentDataRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferencePaymentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConferencePaymentDataService implements IConferencePaymentDataService {

    @Autowired
    ConferencesPaymentDataRepository conferencesPaymentDataRepository;

    @Override
    public ConferencesPaymentData findConferencesPaymentData() {
        return conferencesPaymentDataRepository.findConferencesPaymentData();
    }

    @Override
    public String findBitcoinToken() {
        return conferencesPaymentDataRepository.findConferencesPaymentData().getBitcoinToken();
    }

    @Override
    public PaypalCredentialsDTO findPaypalCredentials() {
        ConferencesPaymentData paymentData = conferencesPaymentDataRepository.findConferencesPaymentData();
        return new PaypalCredentialsDTO(paymentData.getPaypalClientId(), paymentData.getPaypalClientSecret());
    }
}
