package com.bluearcus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.PackAllocation;

public interface PackAllocationRepo extends JpaRepository<PackAllocation, Integer> {

}
