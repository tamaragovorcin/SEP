package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.CartRepository;
import com.example.WebShop.Repository.ProductRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService implements ICartService {


    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart save(NewOrderDTO newOrderDTO) {

        Cart cart = new Cart();
        User registeredUser = getLoggedUser();
        cart.setBuyer(registeredUser);
        cart.setQuantity(newOrderDTO.getQuantity());
        cart.setProduct(productService.findById(newOrderDTO.getProductId()));
        cart.setStatus("CREATED");

        return cartRepository.save(cart);
    }
    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }
    @Override
    public Cart findById(Integer id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public void delete(Cart order) {
        cartRepository.delete(order);
    }

    @Override
    public Cart update(Cart order) {
        return cartRepository.save(order);
    }
}
