package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.DTOs.WebShopDTO;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.WebShop;

import java.util.List;

public interface IWebShopService {
    List<WebShop> findAll ();
    WebShop save(WebShopDTO webShopDTO);
    WebShop findById(Integer id);
    void delete(Integer id);
}
