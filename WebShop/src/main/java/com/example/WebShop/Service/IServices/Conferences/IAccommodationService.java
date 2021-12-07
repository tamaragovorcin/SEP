package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.AccommodationDTO;
import com.example.WebShop.Model.Conferences.Accommodation;

import java.util.List;

public interface IAccommodationService {
    List<Accommodation> findAll ();
    Accommodation save(AccommodationDTO dto);
    Accommodation findById(Integer id);
    void delete(Accommodation accommodation);
    Accommodation update(Accommodation accommodation);
}
