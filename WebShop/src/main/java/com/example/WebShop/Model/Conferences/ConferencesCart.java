package com.example.WebShop.Model.Conferences;

import com.example.WebShop.Model.Product;
import com.example.WebShop.Model.Purchase;
import com.example.WebShop.Model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import com.example.WebShop.Model.Conferences.Conference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public class ConferencesCart {
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
        @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = true, unique = false)
        private Conference conference;

        @JsonBackReference
        @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
        @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = true, unique = false)
        private Accommodation accommodation;

        @JsonBackReference
        @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
        @JoinColumn(name = "transportation_id", referencedColumnName = "id", nullable = true, unique = false)
        private Transportation transportation;

        @Column(name = "quantity", nullable = true)
        private Integer quantity;


        @Column(name = "status", nullable = true)
        private String status;

        @Column(name = "totalPrice", nullable = true)
        private Double totalPrice;

        @JsonIgnore
        @JsonBackReference(value="conferencesPurchase-conferencesCarts")
        @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
        @JoinColumn(name = "conferencesPurchase_id", referencedColumnName = "id", nullable = true, unique = false)
        private ConferencesPurchase conferencesPurchase;

    }

