package com.example.WebShop.DTOs.Conferences;

import com.example.WebShop.Model.Pictures;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewConferenceDTO {
    private  Integer id;

    private String name;

    private String slogan;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private String content;

    private Integer capacity;

    private Double registrationFee;

    private Boolean online;

    private List<String> pictures;
}
