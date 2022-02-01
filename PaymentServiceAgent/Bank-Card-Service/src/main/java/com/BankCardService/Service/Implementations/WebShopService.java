package com.BankCardService.Service.Implementations;


import com.BankCardService.Model.WebShop;
import com.BankCardService.Repository.WebShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebShopService {
    @Autowired
    WebShopRepository webShopRepository;


    public List<WebShop> findAll() {
        return webShopRepository.findAll();
    }

}
