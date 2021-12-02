package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Product;

import java.util.List;

public interface IConferenceService {
    List<Conference> findAll ();
    Conference save(NewConferenceDTO newConferenceDTO);
    Conference findById(Integer id);
    void delete(Integer id);
    Conference update(Conference conference);
}
