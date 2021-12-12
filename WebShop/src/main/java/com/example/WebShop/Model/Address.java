package com.example.WebShop.Model;

import com.example.WebShop.DTOs.AddressDTO;
import com.example.WebShop.Model.Conferences.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address extends AddressDTO implements Serializable {

    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "country")
    private String country;

    @Audited
    @JsonManagedReference
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Purchase> purchase = new HashSet<Purchase>();


    public Address(double latitude, double longitude, String city, String street, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city= city;
        this.street = street;
        this.country = country;
    }
}
