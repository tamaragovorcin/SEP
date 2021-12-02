package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Purchase;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> findAll ();
    Purchase save(OrderDTO newOrderDTO);
    Purchase findById(Integer id);
    void delete(Purchase purchase);
    Purchase update(Purchase purchase);
}
