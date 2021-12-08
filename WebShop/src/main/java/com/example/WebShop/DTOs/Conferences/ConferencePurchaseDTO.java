package com.example.WebShop.DTOs.Conferences;

import com.example.WebShop.DTOs.AddressDTO;
import com.example.WebShop.DTOs.NewOrderDTO;
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
public class ConferencePurchaseDTO {
    private Integer orderId;

    private LocalDate dateOfReservation;

    private List<ConferencesCartDTO> items;

    private AddressDTO address;

    private String status;

    private Integer registeredUserId;
}
