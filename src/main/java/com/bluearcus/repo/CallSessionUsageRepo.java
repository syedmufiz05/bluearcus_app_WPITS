package com.bluearcus.repo;

import com.bluearcus.dto.CallSessionUsageDto;
import com.bluearcus.model.CallSessionUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CallSessionUsageRepo extends JpaRepository<CallSessionUsage, Integer> {
    Optional<CallSessionUsage> findByImsi(String imsi);
    
	Optional<CallSessionUsage> findByImsiOrMsisdn(String imsi, String msisdn);
    
    @Query("select new com.bluearcus.dto.CallSessionUsageDto(csu.id,csu.peerSessionId,csu.msisdn,csu.imsi,csu.calledMsisdn,csu.locationInfo,csu.sessionState,csu.callStartTs,csu.callEndTs,csu.totalSeconds,csu.callStatus) from CallSessionUsage csu")
    List<CallSessionUsageDto> fetchAllCallSessionUsage();
    
    List<CallSessionUsage> findTop5ByOrderByIdDesc();
}
