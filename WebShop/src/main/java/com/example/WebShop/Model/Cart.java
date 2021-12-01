package com.example.WebShop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true, unique = false)
    private User buyer;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true, unique = false)
    private Product product;


    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "quantity", nullable = true)
    private Double quantity;


    @Column(name = "status", nullable = true)
    private String status;

}
