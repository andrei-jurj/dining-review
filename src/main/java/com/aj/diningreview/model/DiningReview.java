package com.aj.diningreview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "reviews")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiningReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "submitted_by")
    private String submittedBy;

    private Long restaurantId;

    @Min(1)
    @Max(5)
    @Column(name = "peanut_score")
    private Integer peanutScore;

    @Min(1)
    @Max(5)
    @Column(name = "egg_score")
    private Integer eggScore;

    @Min(1)
    @Max(5)
    @Column(name = "dairy_score")
    private Integer dairyScore;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "reviewStatus")
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

}
