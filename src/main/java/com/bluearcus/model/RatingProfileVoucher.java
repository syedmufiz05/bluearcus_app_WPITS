package com.bluearcus.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "sms_balance")
	public Integer smsBalance;

	@Column(name = "data_balance")
	public Integer dataBalance;

	@Column(name = "category_offer_list", columnDefinition = "JSON")
	private String categoryOfferList;

	@Column(name = "rates_offer_list", columnDefinition = "JSON")
	private String ratesOfferList;

	public void setCategoryOffer(String categoryOfferList) {
		try {
			this.categoryOfferList = new ObjectMapper().writeValueAsString(categoryOfferList);
			System.out.println(categoryOfferList);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setRatesOffer(String ratesOfferList) {
		try {
			this.ratesOfferList = new ObjectMapper().writeValueAsString(ratesOfferList);
			System.out.println(ratesOfferList);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
