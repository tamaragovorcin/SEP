package com.example.WebShop.DTOs.Conferences;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceCartFrontDTO {
    private Integer conferenceId;

    private String conferenceName;
    private String accommodation;

    private String transportation;

    private Integer registeredUserId;

    private Integer quantity;

    private Integer cartId;

    private String name;

    private Double price;

    private Set<String> pictures;
}
