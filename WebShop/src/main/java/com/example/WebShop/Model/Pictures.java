package com.example.WebShop.Model;


import com.example.WebShop.Model.Conferences.Conference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="pictures_table")
public class Pictures {

    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "name", nullable = true)
    private String name;

    @JsonIgnore
    @JsonBackReference(value="product-pictures")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true, unique = false)
    private Product product;

    @JsonIgnore
    @JsonBackReference(value="conference-pictures")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = true, unique = false)
    private Conference conference;

}
