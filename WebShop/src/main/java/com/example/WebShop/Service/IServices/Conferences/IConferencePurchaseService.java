package com.example.WebShop.Service.IServices.Conferences;

import com.example.WebShop.DTOs.Conferences.ConferencePurchaseDTO;
import com.example.WebShop.DTOs.Conferences.NewConferenceDTO;
import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Conferences.ConferencesPurchase;

import java.util.List;

public interface IConferencePurchaseService {
    List<ConferencesPurchase> findAll ();
    ConferencesPurchase save(ConferencePurchaseDTO dto);
    ConferencesPurchase findById(Integer id);
    void delete(Integer id);
    ConferencesPurchase update(ConferencesPurchase conference);
}
