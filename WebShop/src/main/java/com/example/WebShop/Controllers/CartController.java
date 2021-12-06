package com.example.WebShop.Controllers;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Model.User;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.Implementations.CartService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addToCart(@RequestBody NewOrderDTO newOrderDTO) {
        Cart cart = new Cart();
        try {
             cart = cartService.save(newOrderDTO);
            logger.info("Item with id: " + newOrderDTO.getProductId() + "has just been added to the cart by user " + cartService.getLoggedUser().getUsername());

        }
        catch (Exception e){
            logger.error("Exception while adding product with id: " + newOrderDTO.getProductId()  + "to a cart by user " + cartService.getLoggedUser().getUsername() + ". Error is: " + e);
        }
        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/allUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<NewOrderDTO>> allInCart() {

        BufferedImage img = null;
        List<Cart> carts = new ArrayList<>();
        List<NewOrderDTO> cartDTOS = new ArrayList<NewOrderDTO>();

        try {
            carts =  cartService.findAll();
            User user = cartService.getLoggedUser();
            for (Cart cart : carts) {
                if (cart.getBuyer().getId() == user.getId() && cart.getStatus().equals("CREATED")) {
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

            logger.info("User: " + cartService.getLoggedUser().getUsername()   + "is viewing its cart." );


        } catch (Exception e){
            logger.error("Exception while opening cart of the user: " +  cartService.getLoggedUser().getUsername() + ". Error is: " + e);

        }

        return cartDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(cartDTOS);
    }


    @GetMapping("/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> remove(@PathVariable String id) {

        Cart cart = new Cart();

        try {
            cart = cartService.findById(Integer.parseInt(id));
            cartService.delete(cart);
            logger.info("Item with id: " + id + "has been deleted from the cart by user: " + cartService.getLoggedUser().getUsername());

        }
        catch (Exception e){
            logger.error("Exception while deleting item from cart by user: " +  cartService.getLoggedUser().getUsername() + ". Error is: " + e);

        }
        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully deleted!", HttpStatus.CREATED);
    }

}
