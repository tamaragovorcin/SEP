package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.AccommodationDTO;
import com.example.WebShop.DTOs.Conferences.ConferenceFrontDTO;
import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.DTOs.Conferences.TransportationDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Conferences.Transportation;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Repository.Conferences.ConferenceRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferenceService;
import com.example.WebShop.Service.IServices.IPicturesService;
import com.example.WebShop.Service.Implementations.PicturesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ConferenceServiceImpl implements IConferenceService {
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    private PicturesServiceImpl picturesService;
    @Autowired
    AccommodationServiceImpl accommodationService;
    @Autowired
    TransportationService transportationService;

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference save(NewConferenceDTO dto) {
        Conference conference = new Conference();
        conference.setName(dto.getName());
        conference.setSlogan(dto.getSlogan());
        Address address = new Address(dto.getAddress().getLatitude(), dto.getAddress().getLongitude(), dto.getAddress().getCity(), dto.getAddress().getStreet(), dto.getAddress().getCountry());
        conference.setAddress(address);
        conference.setStartDate(dto.getStartDate());
        conference.setEndDate(dto.getEndDate());
        conference.setContent(dto.getContent());
        conference.setCapacity(dto.getCapacity());
        conference.setRegistrationFee(dto.getRegistrationFee());
        conference.setOnline(dto.getOnline());
        Set<Pictures> pictures = new HashSet<Pictures>();
        conference = conferenceRepository.save(conference);


        for (String s : dto.getPictures()) {
            NewPictureDTO newPictureDTO = new NewPictureDTO();
            System.out.println("faesf" + conference.getId());
            newPictureDTO.setItemId(conference.getId());
            newPictureDTO.setName(s);
            Pictures picture = picturesService.saveConferenceImage(newPictureDTO);
            pictures.add(picture);

        }
        conference.setPictures(pictures);

        return conferenceRepository.save(conference);

    }

    @Override
    public Conference findById(Integer id) {
        return conferenceRepository.findById(id).get();
    }

    @Override
    public void delete(Integer id) {
        conferenceRepository.delete(findById(id));
    }

    @Override
    public Conference update(Conference conference) {
        return null;
    }

    public List<NewConferenceDTO> getAll() {
        BufferedImage img = null;
        List<NewConferenceDTO> newConferenceDTOS = new ArrayList<NewConferenceDTO>();
        for (Conference conference : findAll()) {
            NewConferenceDTO dto = new NewConferenceDTO();
            dto.setId(conference.getId());
            dto.setName(conference.getName());
            dto.setSlogan(conference.getSlogan());
            dto.setContent(conference.getContent());
            dto.setCapacity(conference.getCapacity());
            Address address = new Address();
            address.setCity(conference.getAddress().getCity());
            address.setCountry(conference.getAddress().getCountry());
            address.setLatitude(conference.getAddress().getLatitude());
            address.setLongitude(conference.getAddress().getLongitude());
            address.setStreet(conference.getAddress().getStreet());

            dto.setAddress(address);
            dto.setStartDate(conference.getStartDate());
            dto.setEndDate(conference.getEndDate());
            dto.setOnline(conference.getOnline());
            dto.setRegistrationFee(conference.getRegistrationFee());

            List<String> list = new ArrayList<>();
            for (Pictures pictures : conference.getPictures()) {
                File destination = new File("src/main/resources/images/conferences/" + pictures.getName());
                try {
                    img = ImageIO.read(destination);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(img, "PNG", out);
                    byte[] bytes = out.toByteArray();
                    String base64bytes = Base64.getEncoder().encodeToString(bytes);
                    String src = "data:image/png;base64," + base64bytes;
                    list.add(src);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            dto.setPictures(list);
            newConferenceDTOS.add(dto);

        }
        return  newConferenceDTOS;
    }
    public ConferenceFrontDTO findDtoById(Integer id){
            BufferedImage img = null;
            ConferenceFrontDTO dto = new ConferenceFrontDTO();
            Conference conference = findById(id);

            dto.setId(conference.getId());
            dto.setName(conference.getName());
            dto.setSlogan(conference.getSlogan());
            dto.setContent(conference.getContent());
            dto.setCapacity(conference.getCapacity());
            dto.setAddress(conference.getAddress());
            dto.setStartDate(conference.getStartDate());
            dto.setEndDate(conference.getEndDate());
            dto.setRegistrationFee(conference.getRegistrationFee());
            dto.setOnline(conference.getOnline());
            List<String> list = new ArrayList<>();
            for (Pictures pictures : conference.getPictures()) {
                File destination = new File("src/main/resources/images/conferences/" + pictures.getName());
                try {
                    img = ImageIO.read(destination);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(img, "PNG", out);
                    byte[] bytes = out.toByteArray();
                    String base64bytes = Base64.getEncoder().encodeToString(bytes);
                    String src = "data:image/png;base64," + base64bytes;
                    list.add(src);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            List<AccommodationDTO> accommodations = new ArrayList<AccommodationDTO>();
            for(Accommodation accommodation : conference.getAccommodations()){
                AccommodationDTO accommodationDTO = accommodationService.getDTO(accommodation);
                accommodations.add(accommodationDTO);
            }
            dto.setAccommodations(accommodations);

        List<TransportationDTO> transportations = new ArrayList<TransportationDTO>();
        for(Transportation transportation : conference.getTransportation()){
            TransportationDTO transportationDTO = transportationService.getDTO(transportation);
            transportations.add(transportationDTO);
        }
        dto.setTransportations(transportations);

        dto.setPictures(list);




        return  dto;
    }

}
