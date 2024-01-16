package com.bluearcus.service;

import com.bluearcus.dto.DeviceMgmtDtoNew;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeviceMgmtService {
    ResponseEntity saveDeviceMgmtDetail(DeviceMgmtDtoNew deviceMgmtDto);

    ResponseEntity editDeviceMgmtDetail(Integer deviceId, DeviceMgmtDtoNew deviceMgmtDto);

    ResponseEntity deleteDeviceMgmtDetail(Integer deviceId);

    List<DeviceMgmtDtoNew> fetchAllDeviceMgmtDetail();

    List<DeviceMgmtDtoNew> searchItems(String keyword);
}
