package com.example.WebShop.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewProductDTO {

    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;

    private Set<String> pictures;



    @Override
    public String toString() {
        return "NewItemDTO{" +
                "quantityDTO=" + quantity +
                '}';
    }
}
