package com.example.WebShop.Controllers;

import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Pictures;
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

    @GetMapping("/all")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<NewProductDTO>> hats() {

        BufferedImage img = null;
        List<Product> products = productService.findAll();
        List<NewProductDTO> productDTOS = new ArrayList<NewProductDTO>();
        for (Product product : products) {

            NewProductDTO newProductDTO = new NewProductDTO();
            newProductDTO.setId(product.getId());
            newProductDTO.setQuantity(product.getQuantity());
            newProductDTO.setName(product.getName());
            newProductDTO.setPrice(product.getPrice());


                Set<String> list = new HashSet<String>();
                for (Pictures pictures : product.getPictures()) {
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

                newProductDTO.setPictures(list);


            productDTOS.add(newProductDTO);

        }

        return productDTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(productDTOS);
    }

    @GetMapping(value = "/delete/{id}")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {

        productService.delete(id);

        return ResponseEntity.ok("Success");
    }

}
