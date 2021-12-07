package com.example.WebShop.Controllers;


import com.example.WebShop.DTOs.Conferences.ConferencesCartDTO;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.User;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.Implementations.CartService;
import com.example.WebShop.Service.Implementations.Conferences.ConferenceCartServiceImpl;
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
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ConferenceCartServiceImpl conferenceCartService;


    @PostMapping("/add")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> addReservation(@RequestBody NewOrderDTO newOrderDTO) {

       Cart cart = cartService.save(newOrderDTO);

        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/allUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<NewOrderDTO>> allUser() {

        BufferedImage img = null;
        List<Cart> carts = cartService.findAll();
        List<NewOrderDTO> cartDTOS = new ArrayList<NewOrderDTO>();

        User user = cartService.getLoggedUser();
        for (Cart cart : carts) {
            if(cart.getBuyer().getId()==user.getId() && cart.getStatus().equals("CREATED")) {
                NewOrderDTO cartDTO = new NewOrderDTO();

                cartDTO.setCartId(cart.getId());
                cartDTO.setName(cart.getProduct().getName());
                cartDTO.setQuantity(cart.getQuantity());
                cartDTO.setPrice(cart.getProduct().getPrice());
                cartDTO.setProductId(cart.getProduct().getId());




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

                    cartDTO.setPictures(list);



                cartDTOS.add(cartDTO);
                }


            }





        return cartDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(cartDTOS);
    }


    @GetMapping("/remove/{id}")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> remove(@PathVariable String id) {

        Cart cart = cartService.findById(Integer.parseInt(id));
        cartService.delete(cart);

        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully deleted!", HttpStatus.CREATED);
    }

}
