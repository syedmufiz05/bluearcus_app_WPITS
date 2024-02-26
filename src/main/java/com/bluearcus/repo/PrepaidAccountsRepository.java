package com.bluearcus.repo;

import com.bluearcus.model.PrepaidAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrepaidAccountsRepository extends JpaRepository<PrepaidAccounts, Integer> {

	Optional<PrepaidAccounts> findByAccountId(Integer accountId);
	
	Optional<PrepaidAccounts> findByCustomerId(Integer customerId);

	Optional<PrepaidAccounts> findByMsisdn(String msisdn);

	Optional<PrepaidAccounts> findByImsi(String imsi);
	
}
