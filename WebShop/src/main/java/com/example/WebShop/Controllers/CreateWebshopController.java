package com.example.WebShop.Controllers;

import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.WebShopDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.User;
import com.example.WebShop.Model.WebShop;
import com.example.WebShop.Service.Implementations.CartService;
import com.example.WebShop.Service.Implementations.WebShopServiceImpl;
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
@RequestMapping(value = "/api/webShop", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreateWebshopController {

    @Autowired
    private WebShopServiceImpl webShopService;

    @Autowired
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addWebshop(@RequestBody WebShopDTO webShopDTO) {
        WebShop webShop = new WebShop();
        try {
            webShop = webShopService.save(webShopDTO);
            logger.info("WebShop with name: " + webShopDTO.getName() + "has just been created ");

        }
        catch (Exception e){
            logger.error("Exception while adding new webshop : " + webShopDTO.getName() + ". Error is: " + e);
        }
        return webShop == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Webshop is successfully added!", HttpStatus.CREATED);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<WebShopDTO>> all() {


        List<WebShop> webShops = new ArrayList<>();
        List<WebShopDTO> webShopDTOS = new ArrayList<WebShopDTO>();

        try {
            webShops =  webShopService.findAll();
            User user = cartService.getLoggedUser();
            for (WebShop webShop : webShops) {

                    WebShopDTO webShopDTO = new WebShopDTO();
                    webShopDTO.setWebShopId(webShop.getId());
                    webShopDTO.setName(webShop.getName());
                    webShopDTO.setUserId(webShop.getCreator().getId());


                webShopDTOS.add(webShopDTO);
                }




            logger.info("User: "   + "is viewing  webshops." );


        } catch (Exception e){
            logger.error("Exception while opening list of webshops " + ". Error is: " + e);

        }

        return webShopDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(webShopDTOS);
    }


    @GetMapping("/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> remove(@PathVariable String id) {


        try {
            webShopService.delete(Integer.parseInt(id));
            logger.info("Webshop with id: " + id + "has been deleted ");

        }
        catch (Exception e){
            logger.error("Exception while deleting webshop with id: " +  id + ". Error is: " + e);

        }
        return new ResponseEntity<>("Webshop is successfully deleted!", HttpStatus.CREATED);
    }




}
