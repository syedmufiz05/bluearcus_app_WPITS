package com.bluearcus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "crm_accounts")
@Data
public class CrmAccounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="customer_id")
	private Integer customerId;
	
	@Column(name="customer_type")
	private String customerType;
	
	@Column(name="msisdn")
	private String msisdn;
	
	@Column(name="imsi")
	private String imsi;
	
	@Column(name = "payment_status")
	private Boolean paymentStatus;
}
