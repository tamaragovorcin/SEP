package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.AccommodationDTO;
import com.example.WebShop.DTOs.Conferences.TransportationDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Transportation;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Repository.Conferences.TransportationRepository;
import com.example.WebShop.Service.IServices.Conferences.ITransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class TransportationService implements ITransportationService {
    @Autowired
    TransportationRepository transportationRepository;
    @Autowired
    ConferenceServiceImpl conferenceService;

    @Override
    public List<Transportation> findAll() {
        return transportationRepository.findAll();
    }

    @Override
    public Transportation save(TransportationDTO dto) {
        Transportation transportation = new Transportation();
        transportation.setCompanyName(dto.getCompanyName());
        transportation.setPrice(dto.getPrice());
        transportation.setDeparture(dto.getDeparture());
       // Address address = new Address(dto.getDepartureAddress().getLatitude(), dto.getDepartureAddress().getLongitude(), dto.getDepartureAddress().getCity(), dto.getDepartureAddress().getStreet(), dto.getDepartureAddress().getCountry());
        //transportation.setDepartureAddress(address);
        transportation.setDepartureTime(dto.getDepartureTime());
        transportation.setDepartureDate(dto.getDepartureDate());
        transportation.setConference(conferenceService.findById(dto.getConference()));
        transportation.setDepartureTime(dto.getDepartureTime());


        return transportationRepository.save(transportation);

    }

    @Override
    public Transportation findById(Integer id) {
        return transportationRepository.findById(id).get();
    }

    @Override
    public void delete(Transportation transportation) {
        transportationRepository.delete(transportation);

    }

    @Override
    public Transportation update(Transportation transportation) {
        return null;
    }

    public TransportationDTO getDTO(Transportation transportation) {
        TransportationDTO dto = new TransportationDTO();
        dto.setId(transportation.getId());
        dto.setCompanyName(transportation.getCompanyName());
        dto.setDeparture(transportation.getDeparture());
        dto.setDepartureDate(transportation.getDepartureDate());
        dto.setDepartureTime(transportation.getDepartureTime());
        dto.setConference(transportation.getConference().getId());
        dto.setPrice(transportation.getPrice());
        dto.setDepartureAddress(transportation.getDepartureAddress());

        return dto;
    }
}
