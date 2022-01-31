package com.AuthService.Controller;

import com.AuthService.DTO.PaymentMethodDTO;
import com.AuthService.DTO.WebShopDTO;
import com.AuthService.Model.WebShop;
import com.AuthService.Service.WebShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/webshop")
public class WebShopController {

    @Autowired
    WebShopService webShopService;

    Logger logger = LoggerFactory.getLogger(WebShopController.class);


    @PostMapping("/signup")
    public ResponseEntity<Integer> addWebShop(@RequestBody WebShopDTO webShopDTO) {

        try {
            logger.info("Started registration for a new web shop...");
            Integer userId = webShopService.save(webShopDTO).getId();
            logger.info("New web shop successfully registred!");

            return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error with registration of a new web shop!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody WebShopDTO webShopDTO) {

        try {
            WebShop webShop = webShopService.login(webShopDTO);
            if(webShop!=null) {
                logger.info("Logging in.. Username : " + webShopDTO.getUsername());

                return new ResponseEntity<>(webShop.getWebShopId(), HttpStatus.CREATED);
            }
            logger.info("Bad request when logging in.. Username : " + webShopDTO.getUsername());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            logger.info("Error with login of a web shop!");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/enablePaymentMethod")
    public ResponseEntity<String> enablePaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {

        try {
            webShopService.enablePaymentMethod(paymentMethodDTO);
            logger.info("Payment method " + paymentMethodDTO.getMethodName() +" has been added to the webshop with id : " + paymentMethodDTO.getWebShopId());

        }
        catch (Exception e){
            logger.error("Exception while adding payment methods. Error is: " + e);
        }
        return new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/disablePaymentMethod")
    public ResponseEntity<String> disablePaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {

        try {
            webShopService.disablePaymentMethod(paymentMethodDTO);
            logger.info("Payment method " + paymentMethodDTO.getMethodName() +" has been disabled in the webshop with id : " + paymentMethodDTO.getWebShopId());
        }
        catch (Exception e){
            logger.error("Exception while adding payment methods. Error is: " + e);
        }
        return new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/allMethods/{webShopId}")
    public ResponseEntity<PaymentMethodDTO> paymentMethods(@PathVariable String webShopId) {

        try {
            PaymentMethodDTO paymentMethodDTO = webShopService.getPaymentMethods(Integer.parseInt(webShopId));
            logger.info("Overview of the currently chosen payment methods for web shop with id: " + webShopId );
            return new ResponseEntity<>(paymentMethodDTO, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting payment methods. Error is: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
