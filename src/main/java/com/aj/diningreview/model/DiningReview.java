package com.aj.diningreview.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@Data
public class DiningReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "submitted_by")
    private String submittedBy;

    @Column(name = "peanut_score")
    private Integer peanutScore;

    @Column(name = "egg_score")
    private Integer eggScore;

    @Column(name = "dairy_score")
    private Integer dairyScore;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "status")
    private Status status;

}
