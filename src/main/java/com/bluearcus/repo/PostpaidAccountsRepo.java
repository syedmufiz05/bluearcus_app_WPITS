package com.bluearcus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bluearcus.model.PostpaidAccounts;

public interface PostpaidAccountsRepo extends JpaRepository<PostpaidAccounts, Integer> {
	
	Optional<PostpaidAccounts> findByImsi(String imsi);
}
