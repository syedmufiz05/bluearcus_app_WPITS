package com.bluearcus.repo;

import com.bluearcus.model.PrepaidRoamingAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrepaidRoamingAccountsRepo extends JpaRepository<PrepaidRoamingAccounts, Integer> {
    Optional<PrepaidRoamingAccounts> findByRoamingAccountId(Integer roamingAccountId);
}
