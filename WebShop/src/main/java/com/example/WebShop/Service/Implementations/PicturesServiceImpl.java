package com.example.WebShop.Service.Implementations;

import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Product;
import com.example.WebShop.Repository.PicturesRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferenceService;
import com.example.WebShop.Service.IServices.IPicturesService;
import com.example.WebShop.Service.IServices.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicturesServiceImpl implements IPicturesService {


    @Autowired
    private PicturesRepository picturesRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private IConferenceService conferenceService;


    @Override
    public List<Pictures> findAll() {
        return picturesRepository.findAll();
    }

    @Override
    public Pictures save(NewPictureDTO newPictureDTO) {
        Pictures pictures = new Pictures();
        Product item = productService.findById(newPictureDTO.getItemId());
        pictures.setProduct(item);
        pictures.setName(newPictureDTO.getName());

        return picturesRepository.save(pictures);
    }

    public Pictures saveConferenceImage(NewPictureDTO newPictureDTO) {
        Pictures pictures = new Pictures();
        System.out.println("ID KONFERENCIJE: "+  newPictureDTO.getItemId()
        );
        Conference item = conferenceService.findById(newPictureDTO.getItemId());
        System.out.println("ID KONFERENCIJE: "+item.getName());
        pictures.setConference(item);
        pictures.setName(newPictureDTO.getName());
        return picturesRepository.save(pictures);
    }

    @Override
    public Pictures findById(Integer id) {
        return picturesRepository.findById(id).get();
    }
}
