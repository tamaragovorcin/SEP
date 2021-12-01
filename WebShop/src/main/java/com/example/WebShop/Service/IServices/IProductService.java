package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll ();
    Product save(NewProductDTO newProductDTO);
    Product findById(Integer id);
    void delete(Integer id);
    Product update(Product product);
}
