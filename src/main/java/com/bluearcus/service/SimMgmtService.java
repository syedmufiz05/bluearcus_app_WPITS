package com.bluearcus.service;

import com.bluearcus.dto.SimMgmtDtoNew;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SimMgmtService {
    ResponseEntity saveSimMgmt(SimMgmtDtoNew simMgmtDto);

    ResponseEntity editSimMgmt(Integer simId, SimMgmtDtoNew simMgmtDto);

    ResponseEntity deleteSimMgmt(Integer simId);

    List<SimMgmtDtoNew> getAllSimMgmt();

    List<SimMgmtDtoNew> searchRecord(String keyword);
}
