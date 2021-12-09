package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.Conferences.PaymentMethodConferences;
import com.example.WebShop.Model.PaymentMethod;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.Conferences.PaymentMethodConferencesRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.Conferences.IPaymentMethodConferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentMethodConferencesServiceImpl implements IPaymentMethodConferences {
    @Autowired
    PaymentMethodConferencesRepository paymentMethodConferencesRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<PaymentMethodConferences> findAll() {
        return paymentMethodConferencesRepository.findAll();
    }

    @Override
    public void save(PaymentMethodDTO paymentMethodDTO) {
        List<PaymentMethodConferences> paymentMethodSet = paymentMethodConferencesRepository.findAll();

        for(PaymentMethodConferences paymentMethod: paymentMethodSet){
            delete(paymentMethod);
        }

        for(String a: paymentMethodDTO.getMethods()){
            PaymentMethodConferences paymentMethod = new PaymentMethodConferences();
            if(a.equals("Card")){
                paymentMethod.setMethod(PaymentMethod.Method.CARD);
            }
            if(a.equals("Bitcoin")){
                paymentMethod.setMethod(PaymentMethod.Method.BITCOIN);
            }
            if(a.equals("Paypal")){
                paymentMethod.setMethod(PaymentMethod.Method.PAYPAL);
            }
            if(a.equals("Qr")){
                paymentMethod.setMethod(PaymentMethod.Method.QR);
            }

             paymentMethodConferencesRepository.save(paymentMethod);

        }
    }
    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }
    @Override
    public PaymentMethodConferences findById(Integer id) {
        return paymentMethodConferencesRepository.findById(id).get();
    }

    @Override
    public void delete(PaymentMethodConferences paymentMethod) {
        paymentMethodConferencesRepository.delete(paymentMethod);
    }

    @Override
    public PaymentMethodConferences update(PaymentMethodConferences paymentMethod) {
        return null;
    }
}
