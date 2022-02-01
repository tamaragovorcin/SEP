package com.AuthService.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebShop {

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name="webShopId")
    private Integer webShopId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "merchantID")
    private String merchantID;

    @Column(name = "merchantPassword")
    private String merchantPassword;

    @Column(name = "bank")
    private Integer bank;


    @Column(name = "successUrl")
    private String successUrl;

    @Column(name = "errorUrl")
    private String errorUrl;

    @Column(name = "failureUrl")
    private String failureUrl;

    @Column(name = "enabledPayPal")
    private Boolean enabledPayPal;

    @Column(name = "enabledBitcoin")
    private Boolean enabledBitcoin;

    @Column(name = "enabledCard")
    private Boolean enabledCard;

    @Column(name = "enabledQR")
    private Boolean enabledQR;

}
