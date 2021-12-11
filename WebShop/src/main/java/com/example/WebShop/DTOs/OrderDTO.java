package com.example.WebShop.DTOs;


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
public class OrderDTO {

    private Integer orderId;

    private LocalDate dateOfReservation;

    private List<NewOrderDTO> products;

    private AddressDTO address;

    private String status;

    private Integer registeredUserId;

    private String orderType;
}
