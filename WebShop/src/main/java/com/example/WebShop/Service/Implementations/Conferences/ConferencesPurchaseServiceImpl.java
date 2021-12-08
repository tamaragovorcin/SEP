package com.example.WebShop.Service.Implementations.Conferences;

import com.example.WebShop.DTOs.Conferences.*;
import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.OrderDTO;
import com.example.WebShop.Model.*;
import com.example.WebShop.Model.Conferences.ConferencesCart;
import com.example.WebShop.Model.Conferences.ConferencesPurchase;
import com.example.WebShop.Repository.Conferences.ConferenceCartRepository;
import com.example.WebShop.Repository.Conferences.ConferencePurchaseRepository;
import com.example.WebShop.Repository.UserRepository;
import com.example.WebShop.Service.IServices.Conferences.IConferencePurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ConferencesPurchaseServiceImpl implements IConferencePurchaseService {
    @Autowired
    ConferencePurchaseRepository conferencePurchaseRepository;
    @Autowired
    ConferenceCartServiceImpl conferenceCartService;
    @Autowired
    ConferenceCartRepository conferenceCartRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<ConferencesPurchase> findAll() {
        return conferencePurchaseRepository.findAll();
    }

    @Override
    public ConferencesPurchase save(ConferencePurchaseDTO dto) {
        ConferencesPurchase purchase = new ConferencesPurchase();
        User registeredUser = getLoggedUser();
        purchase.setBuyer(registeredUser);
        purchase.setDate(LocalDate.now());

        Set<ConferencesCart> carts = new HashSet<>();
        ConferencesPurchase conferencesPurchase=  conferencePurchaseRepository.save(purchase);
        for (ConferencesCartDTO c : dto.getItems()) {
            ConferencesCart cart1 = conferenceCartService.findById(c.getCartId());
            cart1.setStatus("ORDERED");
            cart1.setConferencesPurchase(conferencesPurchase);
            conferenceCartRepository.save(cart1);
            carts.add(cart1);

        }
        conferencesPurchase.setStatus("CREATED");
        conferencesPurchase.setConferencesCarts(carts);
        return conferencePurchaseRepository.save(conferencesPurchase);
    }

    @Override
    public ConferencesPurchase findById(Integer id) {
        return conferencePurchaseRepository.findById(id).get();
    }

    @Override
    public void delete(Integer id) {
        conferencePurchaseRepository.delete(findById(id));
    }

    @Override
    public ConferencesPurchase update(ConferencesPurchase conference) {
        return null;
    }

    public User getLoggedUser() {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        return userRepository.findByUsername(email);

    }

    public List<ConferencePurchaseFrontDTO> findUserPurchases() {
        BufferedImage img = null;
        List<ConferencePurchaseFrontDTO> ordersDTOS = new ArrayList<ConferencePurchaseFrontDTO>();

        User registeredUser = getLoggedUser();
        for (ConferencesPurchase order : findAll()) {
            if (order.getBuyer().getId() == registeredUser.getId()) {
                ConferencePurchaseFrontDTO orderDTO = new ConferencePurchaseFrontDTO();
                System.out.println("LALALA");

                orderDTO.setOrderId(order.getId());
                orderDTO.setDateOfReservation(order.getDate());
                orderDTO.setStatus(order.getStatus());

                List<ConferenceCartFrontDTO> newOrderDTOS = new ArrayList<>();
                for (ConferencesCart cart : order.getConferencesCarts()) {
                    ConferenceCartFrontDTO orderDTO1 = new ConferenceCartFrontDTO();
                    orderDTO1.setPrice(cart.getTotalPrice());
                    orderDTO1.setConferenceId(cart.getConference().getId());
                    orderDTO1.setConferenceName(cart.getConference().getName());
                    orderDTO1.setAccommodation(cart.getAccommodation().getName());
                    orderDTO1.setTransportation(cart.getTransportation().getCompanyName());
                    orderDTO1.setCartId(cart.getId());
                    orderDTO1.setQuantity(cart.getQuantity());
                    orderDTO1.setRegisteredUserId(cart.getBuyer().getId());
                    Set<String> list = new HashSet<String>();
                    for (Pictures pictures : cart.getConference().getPictures()) {
                        File destination = new File("src/main/resources/images/" + pictures.getName());
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
                    orderDTO1.setPictures(list);
                    newOrderDTOS.add(orderDTO1);
                }
                orderDTO.setRegisteredUserId(registeredUser.getId());
                orderDTO.setItems(newOrderDTOS);
                ordersDTOS.add(orderDTO);


            }



        }
        return ordersDTOS;
    }
}


