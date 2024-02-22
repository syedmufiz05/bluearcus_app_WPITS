package com.bluearcus.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rating_plan")
@Data
public class RatingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_plan_id")
    private Integer ratingPlanId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_rates_id", referencedColumnName = "id")
    private DestinationRates destinationRates;
}
