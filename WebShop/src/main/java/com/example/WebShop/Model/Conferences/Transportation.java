package com.example.WebShop.Model.Conferences;
import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Pictures;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transportation {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "companyName" )
    private String companyName;

    @Column(name = "departure")
    private String departure;

    @Column(name = "price" )
    private Double price;

    @Column(name = "departureAddress" )
    private Address departureAddress;

    @Column(name = "departureDate" )
    private LocalDate departureDate;

    @Column(name = "departureTime")
    private LocalTime departureTime;

    @JsonIgnore
    @JsonBackReference(value="conference-transportations")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = true, unique = false)
    private Conference conference;

    @JsonManagedReference
    @OneToMany(mappedBy = "transportation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ConferencesCart> cart = new HashSet<ConferencesCart>();
}
