package com.bluearcus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.model.PostpaidAccounts;

public interface PostpaidAccountsRepo extends JpaRepository<PostpaidAccounts, Integer> {
	
	@Query("select new com.bluearcus.dto.PostpaidAccountsDto(pa.accountId,pa.customerId,pa.callingNumber,pa.calledNumber,pa.callStart,pa.callEnd,pa.callDuration,pa.callType,pa.dataOctetsSessionStart,pa.dataOctetsSessionEnd,pa.dataOctetsSessionConsumed,pa.smsDestinationNumber,pa.smsConsumedCount,pa.smsConsumedDate) from PostpaidAccounts pa")
	List<PostpaidAccountsDto> fetchAllPostpaidAccounts();
}
