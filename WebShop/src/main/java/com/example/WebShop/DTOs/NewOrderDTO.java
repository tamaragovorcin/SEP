package com.example.WebShop.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDTO {

    private Integer productId;

    private LocalDate dateOfReservation;

    private Integer registeredUserId;

    private Double quantity;

    private Integer cartId;

    private String name;

    private Double price;

    private Set<String> pictures;

}

