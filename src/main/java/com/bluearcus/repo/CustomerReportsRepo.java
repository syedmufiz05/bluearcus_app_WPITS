package com.bluearcus.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bluearcus.model.CustomerReports;

import jakarta.transaction.Transactional;

public interface CustomerReportsRepo extends JpaRepository<CustomerReports, Integer> {
	
	Optional<CustomerReports> findByImsi(String imsi);
	
	Optional<CustomerReports> findByCustomerId(Integer customerId);
	
	@Query("select cr from CustomerReports cr where (cr.firstName) like LOWER(CONCAT('%', :keyword, '%')) or (cr.lastName) like LOWER(CONCAT('%', :keyword, '%')) or (cr.msisdn) like LOWER(CONCAT('%', :keyword, '%')) or (cr.ekycToken) like LOWER(CONCAT('%', :keyword, '%'))")
    List<CustomerReports> searchReportsByName(@Param("keyword") String keyword);
	
	@Transactional
	void deleteByCustomerId(Integer customerId);
}
