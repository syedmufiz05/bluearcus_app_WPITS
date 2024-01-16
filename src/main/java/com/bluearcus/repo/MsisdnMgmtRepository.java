package com.bluearcus.repo;

import com.bluearcus.dto.MsisdnMgmtDto;
import com.bluearcus.model.MsisdnMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MsisdnMgmtRepository extends JpaRepository<MsisdnMgmt, Integer> {
    Optional<MsisdnMgmt> findByMsisdn(String msisdn);

    @Query("select new com.bluearcus.dto.MsisdnMgmtDto(msisdnMgmt.id,msisdnMgmt.msisdn,msisdnMgmt.category,msisdnMgmt.seriesId,msisdnMgmt.isPrepaid,msisdnMgmt.isPostpaid,msisdnMgmt.isM2M,msisdnMgmt.isSpecialNo,msisdnMgmt.allocationDate,msisdnMgmt.status)from MsisdnMgmt msisdnMgmt")
    List<MsisdnMgmtDto> fetchAllMsisdnMgmtRecord();

    @Query("select msisdnMgmt from MsisdnMgmt msisdnMgmt where (msisdnMgmt.msisdn) like LOWER(CONCAT('%', :keyword, '%')) or (msisdnMgmt.category) like LOWER(CONCAT('%', :keyword, '%'))")
    List<MsisdnMgmt> searchItemsByName(@Param("keyword") String keyword);
}
