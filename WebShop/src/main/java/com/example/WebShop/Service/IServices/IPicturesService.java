package com.example.WebShop.Service.IServices;


import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Pictures;

import java.util.List;

public interface IPicturesService {
    List<Pictures> findAll ();
    Pictures save(NewPictureDTO newPictureDTO);
    Pictures findById(Integer id);
}
