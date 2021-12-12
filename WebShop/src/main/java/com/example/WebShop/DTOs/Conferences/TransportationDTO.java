package com.example.WebShop.DTOs.Conferences;


import com.example.WebShop.DTOs.AddressDTO;
import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Conferences.Conference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportationDTO {
    private Integer id;

    private String companyName;

    private String departure;

    private Double price;

    private AddressDTO departureAddress;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private Integer conference;
}
