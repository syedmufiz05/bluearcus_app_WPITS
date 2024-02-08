package com.bluearcus.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "partner")
@Data
public class Partner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "total_payments", nullable = false)
	private Double totalPayments;
	
	@Column(name = "total_refunds", nullable = false)
	private Double totalRefunds;
	
	@Column(name = "total_payouts", nullable = false)
	private Double totalPayouts;
	
	@Column(name = "due_payout")
	private Double duePayout;
	
	@Column(name = "optlock", nullable = false)
	private int optlock;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "parent_id")
	private int parentId;
	
	@Column(name = "commission_type")
	private String commissionType;
//	
	@Column(name = "frist_name")
	private String fristName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "business_address")
	private String businessAddress;
	
	@Column(name = "business_nature")
	private String businessNature;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name = "document_id")
	private String documentId;
	
	@Column(name = "document_type")
	private String documentType;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	@Column(name = "locallity")
	private String locallity;
	
	@Column(name = "coordinate")
	private String coordinate;
	
	@Column(name = "reason_status")
	private String reasonStatus; 
	
	@Column(name = "is_active")
	private Boolean isActive;

//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private BaseUser baseUser;
//	
//	@OneToMany(mappedBy = "partner", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private List<Customer> customers;
//	
//	@OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<PartnerProductMapping> partnerProductMappings;
//	
//	@ManyToOne
//	@JoinColumn(name = "partner_commission_id")
//	private PartnerCommission partnerCommission;
}
