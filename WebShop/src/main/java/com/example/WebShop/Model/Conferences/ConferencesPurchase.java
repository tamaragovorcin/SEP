package com.example.WebShop.Model.Conferences;


import com.example.WebShop.Model.Address;
import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.User;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferencesPurchase {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true, unique = false)
    private User buyer;

    @JsonIgnore
    @JsonManagedReference(value="conferencesPurchase-conferencesCarts")
    @OneToMany(mappedBy = "conferencesPurchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ConferencesCart> conferencesCarts = new HashSet<ConferencesCart>();

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "status", nullable = true)
    private String status;
}
