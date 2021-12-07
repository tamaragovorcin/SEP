package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.ConferencesCartDTO;
import com.example.WebShop.DTOs.Conferences.FacilityDTO;
import com.example.WebShop.Model.Conferences.ConferencesCart;
import com.example.WebShop.Model.Conferences.Facility;

import java.util.List;

public interface IConferenceCartService {
    List<ConferencesCart> findAll ();
    ConferencesCart save(ConferencesCartDTO dto);
    ConferencesCart findById(Integer id);
    void delete(ConferencesCart conferencesCart);
    ConferencesCart update(ConferencesCart conferencesCart);
}
