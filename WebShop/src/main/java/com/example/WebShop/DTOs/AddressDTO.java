package com.example.WebShop.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {


    private double latitude;


    private double longitude;


    private String city;


    private String street;


    private String country;



}