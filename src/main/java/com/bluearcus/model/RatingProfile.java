package com.bluearcus.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rating_profile")
public class RatingProfile {
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "calling_party")
    private String callingParty;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rating_plan_id", referencedColumnName = "rating_plan_id")
    private RatingPlan ratingPlan;
}
