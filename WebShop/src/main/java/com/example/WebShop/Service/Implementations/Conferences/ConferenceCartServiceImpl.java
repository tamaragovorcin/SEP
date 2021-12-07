package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.ConferenceCartFrontDTO;
import com.example.WebShop.DTOs.Conferences.ConferencesCartDTO;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.example.WebShop.Model.Conferences.ConferencesCart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.User;
import com.example.WebShop.Repository.Conferences.ConferenceCartRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferenceCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ConferenceCartServiceImpl implements IConferenceCartService {
    @Autowired
    ConferenceCartRepository conferenceCartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConferenceServiceImpl conferenceService;
    @Autowired
    TransportationService transportationService;
    @Autowired
    AccommodationServiceImpl accommodationService;

    @Override
    public List<ConferencesCart> findAll() {
        return conferenceCartRepository.findAll();
    }

    @Override
    public ConferencesCart save(ConferencesCartDTO dto) {
        ConferencesCart cart = new ConferencesCart();
        User registeredUser = getLoggedUser();
        cart.setBuyer(registeredUser);
        cart.setQuantity(dto.getQuantity());
        cart.setConference(conferenceService.findById(dto.getConferenceId()));
        cart.setTotalPrice(conferenceService.findById(dto.getConferenceId()).getRegistrationFee());
        cart.setStatus("CREATED");

        return conferenceCartRepository.save(cart);
    }

    @Override
    public ConferencesCart findById(Integer id) {
        return conferenceCartRepository.findById(id).get();
    }

    @Override
    public void delete(ConferencesCart conferencesCart) {
        conferenceCartRepository.delete(conferencesCart);
    }

    @Override
    public ConferencesCart update(ConferencesCart conferencesCart) {
        return null;
    }
    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }
    public List<ConferenceCartFrontDTO> getCartConferencesForUser(){

        BufferedImage img = null;
        List<ConferenceCartFrontDTO> conferenceDTOS = new ArrayList<ConferenceCartFrontDTO>();
        User user = getLoggedUser();
        for (ConferencesCart cart : findAll()) {
            if(cart.getBuyer().getId()==user.getId() && cart.getStatus().equals("CREATED")) {
                ConferenceCartFrontDTO cartDTO = new ConferenceCartFrontDTO();
                cartDTO.setCartId(cart.getId());
                cartDTO.setName(cart.getConference().getName());
                cartDTO.setQuantity(cart.getQuantity());
                cartDTO.setConferenceId(cart.getConference().getId());
                cartDTO.setPrice(cart.getTotalPrice());
                if(cart.getAccommodation()!=null){
                    cartDTO.setAccommodation(accommodationService.getDTO(cart.getAccommodation()).getName());

                }
                if(cart.getTransportation() != null){
                    cartDTO.setTransportation(transportationService.getDTO(cart.getTransportation()).getCompanyName());

                }


                Set<String> list = new HashSet<String>();
                for (Pictures pictures : cart.getConference().getPictures()) {
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

                cartDTO.setPictures(list);


                conferenceDTOS.add(cartDTO);
            }


        }
        return conferenceDTOS;
    }
    public ConferencesCart addTransportationToCart(ConferencesCartDTO dto){
        ConferencesCart conferencesCart  =  findById(dto.getCartId());
        conferencesCart.setTransportation(transportationService.findById(dto.getTransportationId()));
        conferencesCart.setTotalPrice(conferencesCart.getTotalPrice()+transportationService.findById(dto.getTransportationId()).getPrice());

        return conferenceCartRepository.save(conferencesCart);
    }
    public ConferencesCart addAccommodationToCart(ConferencesCartDTO dto){
        ConferencesCart conferencesCart  =  findById(dto.getCartId());
        Accommodation accommodation = accommodationService.findById(dto.getAccommodationId());
        if(accommodation.getMaxCapacity()-conferencesCart.getQuantity() >=0) {
            conferencesCart.setAccommodation(accommodation);
            accommodation.setMaxCapacity(accommodation.getMaxCapacity() - (conferencesCart.getQuantity()));
            conferencesCart.setTotalPrice(conferencesCart.getTotalPrice()+accommodation.getPrice());
            return conferenceCartRepository.save(conferencesCart);
        }else{
            return null;
        }
    }
}
