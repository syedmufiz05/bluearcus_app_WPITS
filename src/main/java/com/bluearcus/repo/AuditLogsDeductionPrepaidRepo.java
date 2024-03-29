package com.bluearcus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bluearcus.model.AuditLogsDeductionPrepaid;

@Repository
public interface AuditLogsDeductionPrepaidRepo extends JpaRepository<AuditLogsDeductionPrepaid, Integer> {

	List<AuditLogsDeductionPrepaid> findAllByMsisdnOrderByCreatedDateDesc(String msisdn);

}
