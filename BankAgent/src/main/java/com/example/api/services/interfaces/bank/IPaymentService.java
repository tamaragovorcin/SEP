package com.example.api.services.interfaces.bank;
import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.PaymentRequestDTO;
import com.example.api.DTOs.PaymentResponseDTO;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.PaymentRequest;

import java.util.List;

public interface IPaymentService {
    PaymentRequest getPaymentRequest(Integer id);
    PaymentResponseDTO getPaymentResponse(PaymentRequestDTO id);
    String confirmPayment(AccountDTO clientDTO, Integer paymentRequestId);
}
