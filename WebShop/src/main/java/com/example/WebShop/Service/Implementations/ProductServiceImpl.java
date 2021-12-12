package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.EditProductDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Repository.ProductRepository;
import com.example.WebShop.Service.IServices.IPicturesService;
import com.example.WebShop.Service.IServices.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IPicturesService picturesService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(NewProductDTO newProductDTO) {
        Product product = new Product();
        product.setName(newProductDTO.getName());
        product.setPrice(newProductDTO.getPrice());
        product.setQuantity(newProductDTO.getQuantity());

        Set<Pictures> pictures = new HashSet<Pictures>();
        product = productRepository.save(product);



        for(String s: newProductDTO.getPictures()) {

            NewPictureDTO newPictureDTO = new NewPictureDTO();
            System.out.println("faesf" + product.getId());
            newPictureDTO.setItemId(product.getId());
            newPictureDTO.setName(s);
            Pictures picture = picturesService.save(newPictureDTO);
            pictures.add(picture);

        }
        product.setPictures(pictures);

        Product product1 = productRepository.save(product);

        return product1;
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void delete(Integer id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Override
    public Product update(EditProductDTO product) {
        Product product1 = findById(product.getProductId());
        product1.setQuantity(product.getQuantity());
        product1.setPrice(product.getPrice());
        product1.setName(product.getName());
        return productRepository.save(product1);
    }

}