package com.example.WebShop.Model.Conferences;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferencesPaymentData {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "bitcoinToken",  nullable = false )
    private String bitcoinToken;

    @Column(name = "paypalClientId",  nullable = false )
    private String paypalClientId;

    @Column(name = "paypalClientSecret",  nullable = false )
    private String paypalClientSecret;
}
