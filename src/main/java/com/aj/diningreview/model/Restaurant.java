package com.aj.diningreview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "restaurant",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndZip",
                columnNames = { "name", "zip" }) })
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Restaurant name mandatory")
    @Column(name = "name")
    private String name;

    @Column(name = "zip")
    private String zip;

    @Column(name = "peanut_allergy_rating")
    private Double peanutAllergyRating;

    @Column(name = "egg_allergy_rating")
    private Double eggAllergyRating;

    @Column(name = "dairy_allergy_rating")
    private Double dairyAllergyRating;

    @Column(name = "overall_rating")
    private Double overallRating;

}
