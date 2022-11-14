package com.aj.diningreview.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "restaurant",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndZip",
                columnNames = { "name", "zip" }) })
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "zip")
    private String zip;

    @Column(name = "peanut_allergy_rating")
    private Integer peanutAllergyRating;

    @Column(name = "egg_allergy_rating")
    private Integer eggAllergyRating;

    @Column(name = "dairy_allergy_rating")
    private Integer dairyAllergyRating;

    @Column(name = "overall_rating")
    private Float overallRating;

}
