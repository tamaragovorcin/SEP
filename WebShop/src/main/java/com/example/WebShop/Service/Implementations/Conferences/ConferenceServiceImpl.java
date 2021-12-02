package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Repository.Conferences.ConferenceRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferenceService;
import com.example.WebShop.Service.IServices.IPicturesService;
import com.example.WebShop.Service.Implementations.PicturesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConferenceServiceImpl implements IConferenceService {
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    private PicturesServiceImpl picturesService;

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference save(NewConferenceDTO dto) {
        Conference conference = new Conference();
        conference.setName(dto.getName());
        conference.setSlogan(dto.getSlogan());
        conference.setLocation(dto.getLocation());
        conference.setStartDate(dto.getStartDate());
        conference.setEndDate(dto.getEndDate());
        conference.setContent(dto.getContent());
        conference.setCapacity(dto.getCapacity());

        Set<Pictures> pictures = new HashSet<Pictures>();
        conference = conferenceRepository.save(conference);



        for(String s: dto.getPictures()) {
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

    }

    @Override
    public Conference update(Conference conference) {
        return null;
    }
}
