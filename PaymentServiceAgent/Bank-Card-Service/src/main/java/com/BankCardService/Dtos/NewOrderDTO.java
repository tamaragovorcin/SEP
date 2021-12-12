package com.BankCardService.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDTO {

    private Integer productId;

    private Integer registeredUserId;

    private Integer quantity;

    private Integer cartId;

    private String name;

    private Double price;

    private Set<String> pictures;

    @Override
    public String toString() {
        return "NewOrderDTO{" +
                "productId=" + productId +
                ", registeredUserId=" + registeredUserId +
                ", quantity=" + quantity +
                ", cartId=" + cartId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

