package com.example.WebShop.DTOs.Conferences;

import com.example.WebShop.DTOs.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private  Integer id;

    private Integer conference;

    private String name;

    private String description;

    private AddressDTO address;

    private String location;

    private Integer maxCapacity;

    private Double price;

    private List<String> pictures;
}
