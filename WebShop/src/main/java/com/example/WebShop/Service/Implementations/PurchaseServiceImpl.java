package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.DTOs.UpdatePurchaseStatusDTO;
import com.example.WebShop.Model.*;
import com.example.WebShop.Repository.CartRepository;
import com.example.WebShop.Repository.PurchaseRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.ICartService;
import com.example.WebShop.Service.IServices.IPurchaseService;
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

    public Purchase updatePurchase(UpdatePurchaseStatusDTO updatePurchaseStatusDTO) {
        Purchase purchaseUpdate = findById(Integer.parseInt(updatePurchaseStatusDTO.getPaymentId()));
        purchaseUpdate.setStatus(updatePurchaseStatusDTO.getStatus());
        return purchaseRepository.save(purchaseUpdate);
    }

    public OrderDTO getPurchaseById(int parseInt) {
        Purchase order = findById(parseInt);
        BufferedImage img = null;

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(order.getId());
        orderDTO.setDateOfReservation(order.getDate());
        Address address = new Address();
        address.setCity(order.getAddress().getCity());
        address.setCountry(order.getAddress().getCountry());
        address.setLatitude(order.getAddress().getLatitude());
        address.setLongitude(order.getAddress().getLongitude());
        address.setStreet(order.getAddress().getStreet());

        orderDTO.setAddress(address);
        orderDTO.setStatus(order.getStatus());

        List<NewOrderDTO> newOrderDTOS = new ArrayList<>();
        for (Cart cart : order.getCart()) {
            NewOrderDTO orderDTO1 = new NewOrderDTO();
            orderDTO1.setPrice(cart.getProduct().getPrice());
            orderDTO1.setProductId(cart.getProduct().getId());
            orderDTO1.setName(cart.getProduct().getName());
            orderDTO1.setCartId(cart.getId());
            orderDTO1.setQuantity(cart.getQuantity());
            orderDTO1.setRegisteredUserId(cart.getBuyer().getId());
            Set<String> list = new HashSet<String>();
            for (Pictures pictures : cart.getProduct().getPictures()) {
                File destination = new File("src/main/resources/images/" + pictures.getName());
                try {
                    img = ImageIO.read(destination);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(img, "PNG", out);
                    byte[] bytes = out.toByteArray();
                    String base64bytes = Base64.getEncoder().encodeToString(bytes);
                    String src = "data:image/png;base64," + base64bytes;
                    list.add(src);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            orderDTO1.setPictures(list);
            newOrderDTOS.add(orderDTO1);
        }
        orderDTO.setProducts(newOrderDTOS);
        return orderDTO;

    }
}
