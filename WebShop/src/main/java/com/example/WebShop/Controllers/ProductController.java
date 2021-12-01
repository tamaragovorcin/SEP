package com.example.WebShop.Controllers;

import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Service.Implementations.ProductServiceImpl;
import javassist.expr.NewArray;
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
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody NewProductDTO newProductDTO) {

        Product product = productService.save(newProductDTO);

        return product == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Product is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    ResponseEntity<String> hello(@RequestParam("file") MultipartFile file) throws IOException {

        BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        File destination = new File("src/main/resources/images/" + file.getOriginalFilename());
        ImageIO.write(src, "png", destination);

        return new ResponseEntity<>("Image is successfully added!", HttpStatus.CREATED);
    }


}
