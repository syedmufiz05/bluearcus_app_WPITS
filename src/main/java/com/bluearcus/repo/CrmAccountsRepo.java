package com.bluearcus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.CrmAccounts;

import jakarta.transaction.Transactional;

public interface CrmAccountsRepo extends JpaRepository<CrmAccounts, Integer> {
	Optional<CrmAccounts> findByImsi(String imsi);

	Optional<CrmAccounts> findByCustomerId(Integer customerId);

	@Transactional
	void deleteByCustomerId(Integer customerId);
}
