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

@Data
@Entity
@Table(name = "rating_profile_voucher")
public class RatingProfileVoucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "pack_name")
	public String packName;

	@Column(name = "pack_type")
	public String packType;

	@Column(name = "call_balance")
	public Integer callBalance;

	@Column(name = "call_balance_parameter")
	public String callBalanceParameter;

	@Column(name = "assigned_call_balance")
	public String assignedCallBalance;

	@Column(name = "sms_balance")
	public Integer smsBalance;

	@Column(name = "data_balance")
	public Integer dataBalance;

	@Column(name = "data_balance_parameter")
	public String dataBalanceParameter;

	@Column(name = "assigned_data_balance")
	public String assignedDataBalance;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "rates_offer")
	private String ratesOffer;
}
