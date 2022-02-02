package com.example.api.entities.bank;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

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
public class Merchant {
    @Id
    @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "merchant_id", nullable = false, unique = true)
    private String merchantId;


    @Column(name = "merchant_password", nullable = false, unique = true)
    private String merchantPassword;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
