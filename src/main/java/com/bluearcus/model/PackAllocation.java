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
@Table(name = "pack_allocation")
@Data
public class PackAllocation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "activation_date")
	private Date activationDate;

	@Column(name = "expiration_date")
	private Date expirationDate;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customer_id", referencedColumnName = "account_id")
	private PrepaidAccounts prepaidAccount;
}
