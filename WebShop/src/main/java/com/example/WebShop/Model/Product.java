package com.example.WebShop.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "price",  nullable = false )
    private Double price;

    @Column(name = "quantity",  nullable = false )
    private Integer quantity;

    @Column(name = "name",  nullable = false )
    private String name;

    @JsonIgnore
    @JsonManagedReference(value="product-pictures")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pictures> pictures = new HashSet<Pictures>();

    @JsonManagedReference(value="product-productinorder")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductInOrder> productInOrder = new HashSet<ProductInOrder>();
}
