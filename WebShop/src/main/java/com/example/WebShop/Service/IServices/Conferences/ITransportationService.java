package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.FacilityDTO;
import com.example.WebShop.DTOs.Conferences.TransportationDTO;
import com.example.WebShop.Model.Conferences.Facility;
import com.example.WebShop.Model.Conferences.Transportation;

import java.util.List;

public interface ITransportationService {
    List<Transportation> findAll ();
    Transportation save(TransportationDTO dto);
    Transportation findById(Integer id);
    void delete(Transportation transportation);
    Transportation update(Transportation transportation);
}
