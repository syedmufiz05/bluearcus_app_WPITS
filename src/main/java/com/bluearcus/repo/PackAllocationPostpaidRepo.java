package com.bluearcus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.PackAllocationPostpaid;

public interface PackAllocationPostpaidRepo extends JpaRepository<PackAllocationPostpaid, Integer> {
	Optional<PackAllocationPostpaid> findByMsisdn(String msisdn);
}
