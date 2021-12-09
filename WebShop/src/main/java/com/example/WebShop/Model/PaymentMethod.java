package com.example.WebShop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

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
    private Method method;



}
