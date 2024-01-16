package com.bluearcus.repo;

import com.bluearcus.dto.InventoryMgmtDtoList;
import com.bluearcus.model.InventoryMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryMgmtRepository extends JpaRepository<InventoryMgmt, Integer> {
    @Query("select new com.bluearcus.dto.InventoryMgmtDtoList(invMgmt.id,invMgmt.imsi,invMgmt.pImsi,invMgmt.batchId,invMgmt.vendorId,invMgmt.msisdn,invMgmt.status,invMgmt.provStatus,invMgmt.allocationDate,invMgmt.activationDate)from InventoryMgmt invMgmt")
    List<InventoryMgmtDtoList> fetchAllInventoriesMgmt();

    Optional<InventoryMgmt> findByImsi(String imsi);

    @Query("select invMgmt from InventoryMgmt invMgmt where (invMgmt.imsi) like LOWER(CONCAT('%', :keyword, '%')) or (invMgmt.pImsi) like LOWER(CONCAT('%', :keyword, '%')) or (invMgmt.msisdn) like LOWER(CONCAT('%', :keyword, '%'))")
    List<InventoryMgmt> searchItemsByName(@Param("keyword") String keyword);
}
