package com.BitCoinService.Model;

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
public class BitcoinPayment {

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name="paymentId")
    private String paymentId;

    @Column(name = "total")
    private String total;

    @Column(name = "currency")
    private String currency;

    @Column(name = "createTime")
    private String createTime;
}