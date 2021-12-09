package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.PaymentMethod;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.CartRepository;
import com.example.WebShop.Repository.PaymentMethodRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.ICartService;
import com.example.WebShop.Service.IServices.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService {


    @Autowired
    private PaymentMethodRepository paymentRepository;


    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PaymentMethod> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void save(PaymentMethodDTO paymentMethodDTO) {

        List<PaymentMethod> paymentMethodSet = paymentRepository.findAll();

        for(PaymentMethod paymentMethod: paymentMethodSet){
            delete(paymentMethod);
        }

        for(String a: paymentMethodDTO.getMethods()){
            PaymentMethod paymentMethod = new PaymentMethod();
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

            paymentRepository.save(paymentMethod);
        }

    }
    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }
    @Override
    public PaymentMethod findById(Integer id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public void delete(PaymentMethod order) {
        paymentRepository.delete(order);
    }

    @Override
    public PaymentMethod update(PaymentMethod order) {
        return paymentRepository.save(order);
    }
}
