package com.example.WebShop.Controllers;

import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Service.Implementations.CartService;
import com.example.WebShop.Service.Implementations.ProductServiceImpl;
import javassist.expr.NewArray;
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
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addProduct(@RequestBody NewProductDTO newProductDTO) {
        Product product = new Product();
        try {
            product = productService.save(newProductDTO);
            logger.info("Adding new product: " + newProductDTO.getName());
        }
        catch (Exception e){
            logger.error("Exception while adding new product. Error is: " + e);
        }
        return product == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>("Product is successfully added!", HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    ResponseEntity<String> addPicture(@RequestParam("file") MultipartFile file) throws IOException {

        try {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            File destination = new File("src/main/resources/images/" + file.getOriginalFilename());
            ImageIO.write(src, "png", destination);
            logger.info("Uploading picture");
        }
        catch (Exception e ){
            logger.error("Exception while uploading new picture. Error is: " + e);
        }
        return new ResponseEntity<>("Image is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<NewProductDTO>> allProducts() {

        BufferedImage img = null;
        List<Product> products = new ArrayList<>();
        List<NewProductDTO> productDTOS = new ArrayList<NewProductDTO>();

        try{
        products = productService.findAll();
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

            logger.info("User " +  cartService.getLoggedUser().getUsername() + " is overviewing products.");

        }}
        catch (Exception e){
            logger.error("Exception while overviewing products by user: " + cartService.getLoggedUser().getUsername() +" . Error is: " + e);
        }

        return productDTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(productDTOS);
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {

        try {
            productService.delete(id);
            logger.info("Deleting product with id: " +  id);

        }
        catch (Exception e){
            logger.error("Exception while deleting product with id: " + id + ". Error is: " + e);

        }

        return ResponseEntity.ok("Success");
    }

}
