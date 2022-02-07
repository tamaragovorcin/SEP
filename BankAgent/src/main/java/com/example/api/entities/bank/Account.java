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

    @Column(name = "cardHolderUCIN", nullable = false, unique = true)
    private String cardHolderUCIN;

    @Column(name = "PAN", nullable = false, unique = true)
    private String PAN;

    @Column(name = "cardSecurityCode", nullable = false)
    private String cardSecurityCode;

    @Column(name = "giroNumber", nullable = false)
    private String giroNumber;

    @Column(name = "referenceNumber", nullable = false, unique = true)
    private String referenceNumber;

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


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardHolderUCIN='" + cardHolderUCIN + '\'' +
                ", PAN='" + PAN + '\'' +
                ", cardSecurityCode='" + cardSecurityCode + '\'' +
                ", expirationDate=" + expirationDate +
                ", bank=" + bank +
                ", availableFunds=" + availableFunds +
                ", reservedFunds=" + reservedFunds +
                '}';
    }
}
