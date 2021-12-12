package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.CartRepository;
import com.example.WebShop.Repository.ProductRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.ICartService;
import com.example.WebShop.Service.Implementations.Conferences.ConferenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConferenceServiceImpl conferenceService;


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

        Product product = productService.findById(newOrderDTO.getProductId());
        product.setQuantity(product.getQuantity() - newOrderDTO.getQuantity());
        productRepository.save(product);
        cart.setProduct(product);
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
        Product product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);
        cartRepository.delete(order);
    }

    @Override
    public Cart update(Cart order) {
        return cartRepository.save(order);
    }

}
