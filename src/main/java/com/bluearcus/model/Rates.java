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
@Table(name = "rates")
@Data
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dest_name")
    private String destName;

    @Column(name = "dest_type")
    private String destType;

    @Column(name = "rates_index")
    private Integer ratesIndex;

    @Column(name = "description")
    private String description;

    @Column(name = "is_rates_active")
    private Boolean isRatesActive;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    private AccessLogs accessLogs;
}
