package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Purchase;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.CartRepository;
import com.example.WebShop.Repository.PurchaseRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.ICartService;
import com.example.WebShop.Service.IServices.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PurchaseServiceImpl implements IPurchaseService {


    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CartService cartService;


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase save(OrderDTO newOrderDTO) {

        Purchase purchase = new Purchase();
        User registeredUser = getLoggedUser();
        purchase.setBuyer(registeredUser);
        purchase.setDate(LocalDate.now());
        Address address = new Address(newOrderDTO.getAddress().getLatitude(), newOrderDTO.getAddress().getLongitude(), newOrderDTO.getAddress().getCity(), newOrderDTO.getAddress().getStreet(), newOrderDTO.getAddress().getCountry());
        purchase.setAddress(address);

        Set<Cart> cart = new HashSet<>();

        for( NewOrderDTO c: newOrderDTO.getProducts()){

            Cart cart1 = cartService.findById(c.getCartId());
            cart1.setStatus("ORDERED");
            cartRepository.save(cart1);
            cart.add(cart1);

        }
        purchase.setCart(cart);
        purchase.setStatus("CREATED");

        return purchaseRepository.save(purchase);
    }
    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }
    @Override
    public Purchase findById(Integer id) {
        return purchaseRepository.findById(id).get();
    }

    @Override
    public void delete(Purchase order) {
        purchaseRepository.delete(order);
    }

    @Override
    public Purchase update(Purchase order) {
        return purchaseRepository.save(order);
    }
}
