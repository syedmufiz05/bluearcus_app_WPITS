package com.bluearcus.repo;

import com.bluearcus.dto.SimMgmtDto;
import com.bluearcus.model.SimMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimMgmtRepository extends JpaRepository<SimMgmt, Integer> {
    @Query("select new com.bluearcus.dto.SimMgmtDto(simMgmt.id,simMgmt.imsi,simMgmt.batchNo,simMgmt.batchDate,simMgmt.allocationDate,simMgmt.simType,simMgmt.keyId,simMgmt.authId,simMgmt.vendorName,simMgmt.status)from SimMgmt simMgmt")
    List<SimMgmtDto> fetchAllSimMgmt();

    Optional<SimMgmt> findByImsi(String imsi);

    @Query("select simMgmt from SimMgmt simMgmt where (simMgmt.imsi) like LOWER(CONCAT('%', :keyword, '%')) or (simMgmt.batchNo) like LOWER(CONCAT('%', :keyword, '%')) or (simMgmt.simType) like LOWER(CONCAT('%', :keyword, '%')) or (simMgmt.vendorName) like LOWER(CONCAT('%', :keyword, '%'))")
    List<SimMgmt> searchItemsByName(String keyword);
}
