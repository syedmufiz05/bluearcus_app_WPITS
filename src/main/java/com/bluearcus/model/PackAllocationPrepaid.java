package com.bluearcus.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pack_allocation_prepaid")
@Data
public class PackAllocationPrepaid {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "activation_date")
	private Date activationDate;

	@Column(name = "expiration_date")
	private Date expirationDate;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "imsi")
	private String imsi;
}