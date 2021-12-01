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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrder {

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @JsonBackReference(value="product-productinorder")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, unique = false)
    private Product product;

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "size", nullable = true)
    private String size;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "quantity", nullable = true)
    private String quantity;

    @ManyToMany(mappedBy = "products")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Set<Purchase> purchases = new HashSet<Purchase>();

}
