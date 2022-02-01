package com.BankCardService.Service.Interfaces;


import com.BankCardService.Dtos.AccountDTO;
import com.BankCardService.Dtos.PaymentRequestDTO;
import com.BankCardService.Dtos.PaymentResponseDTO;
import com.BankCardService.Model.PaymentRequest;

public interface IPaymentService {
    PaymentRequest getPaymentRequest(Integer id);
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO);
}
