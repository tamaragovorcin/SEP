package com.example.WebShop.Controllers;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.Model.*;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.Implementations.CartService;
import com.example.WebShop.Service.Implementations.PurchaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PurchaseController {

    @Autowired
    private PurchaseServiceImpl purchaseService;
    @Autowired
    private IProductService productService;

    @Autowired
    private CartService cartService;


    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addPurchase(@RequestBody OrderDTO newOrderDTO) {

       Purchase purchase = new Purchase();

       try {
           purchase = purchaseService.save(newOrderDTO);
           logger.info("User " + cartService.getLoggedUser().getUsername() + " is making a purchase");

       } catch (Exception e){
           logger.error("Exception while user " +  cartService.getLoggedUser().getUsername() + " is making a purchase Error is: " + e);
       }

        return purchase == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Purchase is successfully made!", HttpStatus.CREATED);
    }


    @GetMapping("/allUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<OrderDTO>> allPurchase() {

        BufferedImage img = null;
        List<Purchase> purchases = new ArrayList<>();
        List<OrderDTO> ordersDTOS = new ArrayList<OrderDTO>();

        try {
            purchases = purchaseService.findAll();
            User registeredUser = cartService.getLoggedUser();
            for (Purchase order : purchases) {
                if (order.getBuyer().getId() == registeredUser.getId()) {
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
                    orderDTO.setRegisteredUserId(registeredUser.getId());
                    orderDTO.setProducts(newOrderDTOS);


                    ordersDTOS.add(orderDTO);

                }


            }
            logger.info("User " + cartService.getLoggedUser().getUsername() + " is overviewing its purchases");

        }
        catch (Exception e){
            logger.error("Exception while user " +  cartService.getLoggedUser().getUsername() + " is viewing his purchases. Error is: " + e);

        }

        return ordersDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(ordersDTOS);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDTO>> all() {

        BufferedImage img = null;
        List<Purchase> purchases = new ArrayList<>();
        List<OrderDTO> ordersDTOS = new ArrayList<OrderDTO>();

        try {
            purchases = purchaseService.findAll();
            User registeredUser = cartService.getLoggedUser();
            for (Purchase order : purchases) {

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
                orderDTO.setRegisteredUserId(registeredUser.getId());
                orderDTO.setProducts(newOrderDTOS);


                ordersDTOS.add(orderDTO);


            }
            logger.info("Admin is overviewing purchases");

        }
        catch (Exception e){
            logger.error("Exception while admin is viewing purchases. Error is: " + e);

        }

        return ordersDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(ordersDTOS);
    }


}
