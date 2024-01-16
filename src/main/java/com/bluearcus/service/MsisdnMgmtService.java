package com.bluearcus.service;

import com.bluearcus.dto.MsisdnMgmtDtoNew;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MsisdnMgmtService {
    ResponseEntity saveMsisdnMgmt(MsisdnMgmtDtoNew msisdnMgmtDto);

    ResponseEntity editMsisdnMgmt(Integer msisdnId, MsisdnMgmtDtoNew msisdnMgmtDto);

    ResponseEntity deleteMsisdnMgmt(Integer msisdnId);

    List<MsisdnMgmtDtoNew> getAllMsisdnDetail();

    List<MsisdnMgmtDtoNew> searchRecord(String keyword);
}
