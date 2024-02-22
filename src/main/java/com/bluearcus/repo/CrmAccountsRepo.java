package com.bluearcus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.CrmAccounts;

public interface CrmAccountsRepo extends JpaRepository<CrmAccounts, Integer> {
	Optional<CrmAccounts> findByMsisdn(String msisdn);
}
