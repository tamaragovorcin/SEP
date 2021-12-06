package com.PayPalService.Model;

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
public class PayPalPayment {

    @Id
    @Column(name="paymentId", unique=true, nullable=false)
    private String paymentId;

    @Column(name = "payerId")
    private String payerId;

    @Column(name = "total")
    private String total;

    @Column(name = "currency")
    private String currency;

    @Column(name = "createTime")
    private String createTime;
}
