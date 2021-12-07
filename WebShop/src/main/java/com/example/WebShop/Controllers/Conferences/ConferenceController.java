package com.example.WebShop.Controllers.Conferences;

import com.example.WebShop.DTOs.Conferences.*;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Conferences.ConferencesCart;
import com.example.WebShop.Model.Conferences.Transportation;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Service.Implementations.Conferences.AccommodationServiceImpl;
import com.example.WebShop.Service.Implementations.Conferences.ConferenceCartServiceImpl;
import com.example.WebShop.Service.Implementations.Conferences.ConferenceServiceImpl;
import com.example.WebShop.Service.Implementations.Conferences.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
}
