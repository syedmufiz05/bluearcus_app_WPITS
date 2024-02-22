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
@Table(name = "destination_rates")
@Data
public class DestinationRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rates_tag", referencedColumnName = "id")
    private Rates rates;
}
