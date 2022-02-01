package com.example.api.entities.bank;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy=SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=STRING)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "cardHolderName", nullable = false)
    private String cardHolderName;

    @Column(name = "cardHolderUCIN", nullable = false)
    private String cardHolderUCIN;

    @Column(name = "PAN", nullable = false)
    private String PAN;

    @Column(name = "cardSecurityCode", nullable = false)
    private String cardSecurityCode;

    @Column(name = "expirationDate", nullable = false)
    private YearMonth expirationDate;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = true, unique = false)
    private Bank bank;

    @OneToOne(mappedBy = "account")
    private Merchant merchant;

    @Column
    private Double availableFunds;

    @Column
    private Double reservedFunds;


}
