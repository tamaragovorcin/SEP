package com.example.WebShop.Controllers.Conferences;

import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Service.Implementations.Conferences.ConferenceServiceImpl;
import com.example.WebShop.Service.Implementations.ProductServiceImpl;
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

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody NewConferenceDTO dto) {

        Conference conference = conferenceService.save(dto);

        return conference == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Conference is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/uploadImage")
    ResponseEntity<String> hello(@RequestParam("file") MultipartFile file) throws IOException {

        BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        File destination = new File("src/main/resources/images/" + file.getOriginalFilename());
        ImageIO.write(src, "png", destination);

        return new ResponseEntity<>("Image is successfully added!", HttpStatus.CREATED);
    }


}
