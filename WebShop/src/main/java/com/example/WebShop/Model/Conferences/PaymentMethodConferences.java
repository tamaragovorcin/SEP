package com.example.WebShop.Model.Conferences;

import com.example.WebShop.Model.PaymentMethod;
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
public class PaymentMethodConferences {
    public enum Method {
        PAYPAL,
        CARD,
        BITCOIN,
        QR;
    }

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;


    @Column(name = "method", nullable = true)
    private PaymentMethod.Method method;
}
