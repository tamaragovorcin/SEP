package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.PaymentMethod;
import com.example.WebShop.Model.Product;

import java.util.List;

public interface IPaymentMethodService {
    List<PaymentMethod> findAll ();
    void save(PaymentMethodDTO paymentMethodDTO);
    PaymentMethod findById(Integer id);
    void delete(PaymentMethod id);
    PaymentMethod update(PaymentMethod paymentMethod);
}
