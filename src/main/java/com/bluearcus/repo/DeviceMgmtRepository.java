package com.bluearcus.repo;

import com.bluearcus.dto.DeviceMgmtDto;
import com.bluearcus.model.DeviceMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceMgmtRepository extends JpaRepository<DeviceMgmt, Integer> {
    Optional<DeviceMgmt> findByDeviceId(Integer deviceId);

    void deleteByDeviceId(Integer deviceId);

    @Query("select new com.bluearcus.dto.DeviceMgmtDto(deviceMgmt.deviceId,deviceMgmt.imeiPrimary,deviceMgmt.imeiList,deviceMgmt.userAgent,deviceMgmt.footPrint,deviceMgmt.eirTrackId,deviceMgmt.isESim,deviceMgmt.isUicc,deviceMgmt.registrationDate,deviceMgmt.status)from DeviceMgmt deviceMgmt")
    List<DeviceMgmtDto> fetchAllDeviceMgmtDetail();

    @Query("select d from DeviceMgmt d where (d.imeiPrimary) like LOWER(CONCAT('%', :keyword, '%')) or (d.imeiList) like LOWER(CONCAT('%', :keyword, '%')) or (d.userAgent) like LOWER(CONCAT('%', :keyword, '%')) or (d.footPrint) like LOWER(CONCAT('%', :keyword, '%'))")
    List<DeviceMgmt> searchItemsByName(@Param("keyword") String keyword);
}

