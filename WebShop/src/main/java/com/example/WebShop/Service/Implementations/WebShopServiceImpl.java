package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.DTOs.WebShopDTO;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Model.User;
import com.example.WebShop.Model.WebShop;
import com.example.WebShop.Repository.CreateWebShopRepository;
import com.example.WebShop.Repository.PicturesRepository;
import com.example.WebShop.Service.IServices.Conferences.IAccommodationService;
import com.example.WebShop.Service.IServices.Conferences.IConferenceService;
import com.example.WebShop.Service.IServices.IPicturesService;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.IServices.IWebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebShopServiceImpl implements IWebShopService {
    @Autowired
    private CreateWebShopRepository webShopRepository;

    @Autowired
    private CartService cartService;

    @Override
    public List<WebShop> findAll() {
        return webShopRepository.findAll();
    }

    @Override
    public WebShop save(WebShopDTO webShopDTO) {

        User user = cartService.getLoggedUser();

        WebShop webShop = new WebShop();

        webShop.setName(webShopDTO.getName());
        webShop.setCreator(user);

        return webShopRepository.save(webShop);
    }


    @Override
    public WebShop findById(Integer id) {
        return webShopRepository.findById(id).get();
    }

    @Override
    public void delete(Integer id) {
        WebShop webShop = findById(id);
        webShopRepository.delete(webShop);
    }
}
