package com.bluearcus.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer_reports")
@Data
public class CustomerReports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "ekyc_status")
	private String ekycStatus;

	@Column(name = "ekyc_token")
	private String ekycToken;

	@Column(name = "ekyc_date")
	private LocalDateTime ekycDate;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "pack_id")
	private Integer packId;

	@Column(name = "payment_status")
	private Boolean paymentStatus;
}
