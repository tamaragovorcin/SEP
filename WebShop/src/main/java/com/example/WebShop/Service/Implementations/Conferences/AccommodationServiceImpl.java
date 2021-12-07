package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.AccommodationDTO;
import com.example.WebShop.DTOs.NewPictureDTO;
import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Repository.Conferences.AccommodationRepository;
import com.example.WebShop.Service.IServices.Conferences.IAccommodationService;
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
public class AccommodationServiceImpl implements IAccommodationService {
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    PicturesServiceImpl picturesService;
    @Autowired
    ConferenceServiceImpl conferenceService;

    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Accommodation save(AccommodationDTO dto) {
        Accommodation accommodation = new Accommodation();
        accommodation.setName(dto.getName());
        accommodation.setDescription(dto.getDescription());
       // Address address = new Address(dto.getAddress().getLatitude(), dto.getAddress().getLongitude(), dto.getAddress().getCity(), dto.getAddress().getStreet(), dto.getAddress().getCountry());
        //accommodation.setAddress(address);
        accommodation.setPrice(dto.getPrice());
        accommodation.setMaxCapacity(dto.getMaxCapacity());
        accommodation.setConference(conferenceService.findById(dto.getConference()));
      //  accommodation.setFacilities();
        Set<Pictures> pictures = new HashSet<Pictures>();
        accommodation = accommodationRepository.save(accommodation);


        for (String s : dto.getPictures()) {
            NewPictureDTO newPictureDTO = new NewPictureDTO();
            newPictureDTO.setItemId(accommodation.getId());
            newPictureDTO.setName(s);
            Pictures picture = picturesService.saveAccommodationImages(newPictureDTO);
            pictures.add(picture);

        }
        accommodation.setPictures(pictures);

        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation findById(Integer id) {
        return accommodationRepository.getOne(id);
    }

    @Override
    public void delete(Accommodation accommodation) {
        accommodationRepository.delete(accommodation);
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        return null;
    }

    public AccommodationDTO getDTO(Accommodation accommodation) {
        AccommodationDTO dto = new AccommodationDTO();
        dto.setId(accommodation.getId());
        dto.setName(accommodation.getName());
        dto.setAddress(accommodation.getAddress());
        List<String> list = new ArrayList<>();
        BufferedImage img = null;
        for (Pictures pictures : accommodation.getPictures()) {
            System.out.println(pictures.getName());
            File destination = new File("src/main/resources/images/conferences/accommodation/" + pictures.getName());
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
        dto.setConference(accommodation.getConference().getId());
        dto.setDescription(accommodation.getDescription());
        dto.setPrice(accommodation.getPrice());
        dto.setMaxCapacity(accommodation.getMaxCapacity());


        return dto;
    }

}
