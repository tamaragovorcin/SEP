package com.example.WebShop.Model.Conferences;

import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.example.WebShop.Model.Purchase;
import com.fasterxml.jackson.annotation.*;
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
public class Accommodation {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "name",  nullable = false )
    private String name;

    @Column(name = "description",length=10485760)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @Column(name = "maxCapacity")
    private Integer maxCapacity;

    @Column(name = "price")
    private Double price;

    @JsonIgnore
    @JsonManagedReference(value="accommodation-pictures")
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pictures> pictures = new HashSet<Pictures>();

    @JsonIgnore
    @JsonBackReference(value="conference-accommodations")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = true, unique = false)
    private Conference conference;

    @ManyToMany(mappedBy = "accommodations")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Set<Facility> facilities = new HashSet<Facility>();

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ConferencesCart> cart = new HashSet<ConferencesCart>();



}
