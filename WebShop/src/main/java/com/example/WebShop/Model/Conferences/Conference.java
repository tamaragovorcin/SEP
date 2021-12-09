package com.example.WebShop.Model.Conferences;

import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Conference {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "name",  nullable = false )
    private String name;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "location")
    private String location;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "content",columnDefinition="text", length=10485760)
    private String content;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "registrationFee")
    private Double registrationFee;

    @Column(name = "online")
    private Boolean online;

    @JsonIgnore
    @JsonManagedReference(value="conference-pictures")
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pictures> pictures = new HashSet<Pictures>();

    @JsonIgnore
    @JsonManagedReference(value="conference-transportations")
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transportation> transportation = new HashSet<Transportation>();

    @JsonIgnore
    @JsonManagedReference(value="conference-accommodations")
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Accommodation> accommodations = new HashSet<Accommodation>();

    @JsonManagedReference
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ConferencesCart> cart = new HashSet<ConferencesCart>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;



}
