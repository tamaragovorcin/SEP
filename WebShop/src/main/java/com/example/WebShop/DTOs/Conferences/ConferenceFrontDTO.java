package com.example.WebShop.DTOs.Conferences;

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
public class ConferenceFrontDTO {
    private  Integer id;

    private String name;

    private String slogan;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private String content;

    private Integer capacity;

    private Double registrationFee;

    private  Boolean online;

    private List<String> pictures;

    private List<AccommodationDTO> accommodations;

    private List<TransportationDTO> transportations;
}
