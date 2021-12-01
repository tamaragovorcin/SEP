package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.Model.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> findAll ();
    Cart save(NewOrderDTO newOrderDTO);
    Cart findById(Integer id);
    void delete(Cart cart);
    Cart update(Cart cart);
}
