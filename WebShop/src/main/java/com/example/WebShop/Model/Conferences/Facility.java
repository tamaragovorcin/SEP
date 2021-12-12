package com.example.WebShop.Model.Conferences;

import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Pictures;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "name",  nullable = false )
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accommodation_facilities",
            joinColumns = @JoinColumn(name = "facility_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"))
    private List<Accommodation> accommodations;
}
