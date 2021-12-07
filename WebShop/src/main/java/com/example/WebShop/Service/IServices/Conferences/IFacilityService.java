package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.AccommodationDTO;
import com.example.WebShop.DTOs.Conferences.FacilityDTO;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Facility;

import java.util.List;

public interface IFacilityService {
    List<Facility> findAll ();
    Facility save(FacilityDTO dto);
    Facility findById(Integer id);
    void delete(Facility facility);
    Facility update(Facility facility);
}
