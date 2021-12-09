package com.example.WebShop.Controllers.Conferences;

import com.example.WebShop.Controllers.PaymentMethodController;
import com.example.WebShop.DTOs.Conferences.*;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Conferences.*;
import com.example.WebShop.Model.PaymentMethod;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Service.Implementations.Conferences.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/conference", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConferenceController
{
    @Autowired
    private ConferenceServiceImpl conferenceService;
    @Autowired
    private AccommodationServiceImpl accommodationService;
    @Autowired
    private TransportationService transportationService;
    @Autowired
    private ConferenceCartServiceImpl conferenceCartService;
    @Autowired
    ConferencesPurchaseServiceImpl conferencesPurchaseService;
    @Autowired
    PaymentMethodConferencesServiceImpl paymentMethodService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);




    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody NewConferenceDTO dto) {

        Conference conference = conferenceService.save(dto);

        return conference == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Conference is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/uploadImage")
    ResponseEntity<String> hello(@RequestParam("file") MultipartFile file) throws IOException {

        BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        File destination = new File("src/main/resources/images/conferences/" + file.getOriginalFilename());
        ImageIO.write(src, "png", destination);

        return new ResponseEntity<>("Image is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewConferenceDTO>> getAllConferences() {
        List<NewConferenceDTO> conferenceDTOS = conferenceDTOS = conferenceService.getAll();
        return conferenceDTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(conferenceDTOS);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteConference(@PathVariable Integer id) {

        conferenceService.delete(id);

        return ResponseEntity.ok("Success");
    }
    @PostMapping("/addAccommodation")
    public ResponseEntity<String> addAccommodation(@RequestBody AccommodationDTO dto) {

        Accommodation accommodation = accommodationService.save(dto);

        return accommodation == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Accommodation is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/accommodation/uploadImage")
    ResponseEntity<String> uploadAccommodationImage(@RequestParam("file") MultipartFile file) throws IOException {

        BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        File destination = new File("src/main/resources/images/conferences/accommodation/" + file.getOriginalFilename());
        ImageIO.write(src, "png", destination);

        return new ResponseEntity<>("Image is successfully added!", HttpStatus.CREATED);
    }
    @PostMapping("/addTransportation")
    public ResponseEntity<String> addTransportation(@RequestBody TransportationDTO dto) {

        Transportation transportation = transportationService.save(dto);

        return transportation == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Transportation is successfully added!", HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ConferenceFrontDTO> getConferenceById(@PathVariable Integer id) {
        ConferenceFrontDTO dto = conferenceService.findDtoById(id);
        return dto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(dto);
    }
    @GetMapping("deleteAccommodation/{id}")
    public ResponseEntity<String> removeAccommodation(@PathVariable Integer id) {
        accommodationService.delete(accommodationService.findById(id));
        return ResponseEntity.ok("Success");
    }
    @GetMapping("deleteTransportation/{id}")
    public ResponseEntity<String> deleteTransportation(@PathVariable Integer id) {
        transportationService.delete(transportationService.findById(id));
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addReservation(@RequestBody ConferencesCartDTO newOrderDTO) {

        ConferencesCart cart = conferenceCartService.save(newOrderDTO);

        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<ConferenceCartFrontDTO>> getConferencesFromCart() {
        List<ConferenceCartFrontDTO> cartDTOS = conferenceCartService.getCartConferencesForUser();
        return cartDTOS == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(cartDTOS);
    }
    @PostMapping("/addTransportationToCart")
    public ResponseEntity<String> addTransportationToCart(@RequestBody ConferencesCartDTO dto) {

        ConferencesCart conferencesCart = conferenceCartService.addTransportationToCart(dto);

        return conferencesCart == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Transportation is successfully added!", HttpStatus.CREATED);
    }
    @PostMapping("/addAccommodationToCart")
    public ResponseEntity<String> addAccommodationToCart(@RequestBody ConferencesCartDTO dto) {

        ConferencesCart conferencesCart = conferenceCartService.addAccommodationToCart(dto);

        return conferencesCart == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Transportation is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/removeReservation/{id}")
    public ResponseEntity<String> remove(@PathVariable String id) {

        ConferencesCart cart = conferenceCartService.findById(Integer.parseInt(id));
        conferenceCartService.delete(cart);

        return cart == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>("Item is successfully deleted!", HttpStatus.CREATED);
    }
    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody ConferencePurchaseDTO dto) {

        ConferencesPurchase conference = conferencesPurchaseService.save(dto);

        return conference == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Conference is successfully added!", HttpStatus.CREATED);
    }
    @GetMapping("/userPurchases")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ConferencePurchaseFrontDTO>> allUserPurchases(){

        List<ConferencePurchaseFrontDTO> conference = conferencesPurchaseService.findUserPurchases();

        return conference == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(conference);
    }


    @PostMapping("/paymentMethod/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addPayment(@RequestBody PaymentMethodDTO paymentMethodDTO) {

        try {
            paymentMethodService.save(paymentMethodDTO);
            logger.info("Payment methods had been added to the webshop: ");

        }
        catch (Exception e){
            logger.error("Exception while adding payment methods. Error is: " + e);

        }

        return new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/paymentMethods")
    public ResponseEntity<PaymentMethodDTO> paymentMethods() {

        List<String> list = new ArrayList<>();
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        List<PaymentMethodConferences> paymentMethods = new ArrayList<>();

        try {
            paymentMethods = paymentMethodService.findAll();
            for (PaymentMethodConferences paymentMethod : paymentMethods) {
                if (paymentMethod.getMethod().toString().equals("CARD")) {

                    list.add("Card");
                }
                if (paymentMethod.getMethod().toString().equals("PAYPAL")) {

                    list.add("Paypal");
                }
                if (paymentMethod.getMethod().toString().equals("BITCOIN")) {

                    list.add("Bitcoin");
                }
                if (paymentMethod.getMethod().toString().equals("QR")) {

                    list.add("Qr");
                }
            }
            paymentMethodDTO.setMethods(list);

            logger.info("Overview of the currently chosen payment mathods for webshop" );

        }
        catch (Exception e ){

            logger.error("Exception while overviewing payment methods. Error is: " + e);
        }
        return paymentMethodDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paymentMethodDTO);
    }
}
