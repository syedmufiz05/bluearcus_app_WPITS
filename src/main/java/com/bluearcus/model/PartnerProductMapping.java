package com.bluearcus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "partner_product_mapping")
@Data
public class PartnerProductMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "commission_type")
	private String commissionType;

	@Column(name = "commission_value")
	private String commissionValue;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private Partner partner;

}
