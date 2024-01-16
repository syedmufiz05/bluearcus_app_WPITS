package com.bluearcus.service;

import com.bluearcus.dto.DataSessionUsageDto;
import com.bluearcus.dto.DataSessionUsageDtoNew;

import org.springframework.http.ResponseEntity;
import java.util.List;


public interface DataSessionUsageService {
    ResponseEntity saveDataSessionUsage(DataSessionUsageDto dataSessionUsageDto);

    List<DataSessionUsageDtoNew> getAllDataSessionUsage();
}
