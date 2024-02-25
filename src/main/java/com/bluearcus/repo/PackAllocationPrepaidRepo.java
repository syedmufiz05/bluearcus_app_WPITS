package com.bluearcus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.PackAllocationPrepaid;

public interface PackAllocationPrepaidRepo extends JpaRepository<PackAllocationPrepaid, Integer> {
	Optional<PackAllocationPrepaid> findByCustomerId(Integer customerId);
}
