package com.bluearcus.model;

import java.util.Date;

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
@Table(name = "pack_allocation_postpaid")
@Data
public class PackAllocationPostpaid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "pack_name")
	private String packName;

	@Column(name = "activation_date")
	private Date activationDate;

	@Column(name = "expiration_date")
	private Date expirationDate;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "customer_id")
	private Integer customerId;
}
