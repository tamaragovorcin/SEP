package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.TransportationDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.Conferences.PaymentMethodConferences;
import com.example.WebShop.Model.Conferences.Transportation;
import com.example.WebShop.Model.PaymentMethod;

import java.util.List;

public interface IPaymentMethodConferences {
    List<PaymentMethodConferences> findAll ();
    void save(PaymentMethodDTO dto);
    PaymentMethodConferences findById(Integer id);
    void delete(PaymentMethodConferences paymentMethod);
    PaymentMethodConferences update(PaymentMethodConferences paymentMethod);
}
