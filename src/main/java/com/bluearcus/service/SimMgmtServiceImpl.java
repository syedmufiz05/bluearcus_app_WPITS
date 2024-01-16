package com.bluearcus.service;

import com.bluearcus.dto.SimMgmtDto;
import com.bluearcus.dto.SimMgmtDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.SimMgmt;
import com.bluearcus.repo.SimMgmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SimMgmtServiceImpl implements SimMgmtService {
    @Autowired
    private SimMgmtRepository simMgmtRepository;

    @Override
    public ResponseEntity saveSimMgmt(SimMgmtDtoNew simMgmtDto) {
        Optional<SimMgmt> simMgmt = simMgmtRepository.findByImsi(simMgmtDto.getImsi());
        if (!simMgmt.isPresent()) {
            SimMgmt simMgmtDb = new SimMgmt();
            simMgmtDb.setImsi(simMgmtDto.getImsi() != null ? simMgmtDto.getImsi() : "");
            simMgmtDb.setBatchNo(simMgmtDto.getBatchNo() != null ? simMgmtDto.getBatchNo() : "");
            simMgmtDb.setSimType(simMgmtDto.getSimType() != null ? simMgmtDto.getSimType() : "");
            simMgmtDb.setKeyId(simMgmtDto.getKeyId() != null ? simMgmtDto.getKeyId() : Integer.valueOf(""));
            simMgmtDb.setAuthId(simMgmtDto.getAuthId() != null ? simMgmtDto.getAuthId() : Integer.valueOf(""));
            simMgmtDb.setVendorName(simMgmtDto.getVendorName() != null ? simMgmtDto.getVendorName() : "");
            simMgmtDb.setStatus(simMgmtDto.getStatus() != null ? simMgmtDto.getStatus() : false);
            simMgmtRepository.save(simMgmtDb);
            SimMgmtDtoNew simMgmtDtoNew = new SimMgmtDtoNew(simMgmtDb.getId(), simMgmtDb.getImsi(), simMgmtDb.getBatchNo(), fetchReadableDateTime(simMgmtDb.getBatchDate()), fetchReadableDateTime(simMgmtDb.getAllocationDate()), simMgmtDb.getSimType(), simMgmtDb.getKeyId(), simMgmtDb.getAuthId(), simMgmtDb.getVendorName(), simMgmtDb.getStatus());
            return new ResponseEntity<>(simMgmtDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI Id already exist"));
    }

    @Override
    public ResponseEntity editSimMgmt(Integer simId, SimMgmtDtoNew simMgmtDto) {
        Optional<SimMgmt> simMgmt = simMgmtRepository.findById(simId);
        if (simMgmt.isPresent()) {
            SimMgmt simMgmtDb = simMgmt.get();
            simMgmtDb.setImsi(simMgmtDto.getImsi() != null ? simMgmtDto.getImsi() : simMgmtDb.getImsi());
            simMgmtDb.setBatchNo(simMgmtDto.getBatchNo() != null ? simMgmtDto.getBatchNo() : simMgmtDb.getBatchNo());
            simMgmtDb.setSimType(simMgmtDto.getSimType() != null ? simMgmtDto.getSimType() : simMgmtDb.getSimType());
            simMgmtDb.setKeyId(simMgmtDto.getKeyId() != null ? simMgmtDto.getKeyId() : simMgmtDb.getKeyId());
            simMgmtDb.setAuthId(simMgmtDto.getAuthId() != null ? simMgmtDto.getAuthId() : simMgmtDb.getAuthId());
            simMgmtDb.setVendorName(simMgmtDto.getVendorName() != null ? simMgmtDto.getVendorName() : simMgmtDb.getVendorName());
            simMgmtDb.setStatus(simMgmtDto.getStatus() != null ? simMgmtDto.getStatus() : simMgmtDb.getStatus());
            simMgmtRepository.save(simMgmtDb);
            SimMgmtDtoNew simMgmtDtoNew = new SimMgmtDtoNew(simMgmtDb.getId(), simMgmtDb.getImsi(), simMgmtDb.getBatchNo(), fetchReadableDateTime(simMgmtDb.getBatchDate()), fetchReadableDateTime(simMgmtDb.getAllocationDate()), simMgmtDb.getSimType(), simMgmtDb.getKeyId(), simMgmtDb.getAuthId(), simMgmtDb.getVendorName(), simMgmtDb.getStatus());
            return new ResponseEntity<>(simMgmtDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Sim Id"));
    }

    @Transactional
    @Override
    public ResponseEntity deleteSimMgmt(Integer simId) {
        Optional<SimMgmt> simMgmt = simMgmtRepository.findById(simId);
        if (simMgmt.isPresent()) {
            simMgmtRepository.deleteById(simId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Sim Id"));
    }

    @Override
    public List<SimMgmtDtoNew> getAllSimMgmt() {
        List<SimMgmtDto> simMgmtDbList = simMgmtRepository.fetchAllSimMgmt();
        List<SimMgmtDtoNew> simMgmtDtoList = new ArrayList<>();
        for (SimMgmtDto simMgmtDto : simMgmtDbList) {
            SimMgmtDtoNew simMgmtDtoNew=new SimMgmtDtoNew();
            simMgmtDtoNew.setSimId(simMgmtDto.getSimId());
            simMgmtDtoNew.setImsi(simMgmtDto.getImsi());
            simMgmtDtoNew.setBatchNo(simMgmtDto.getBatchNo());
            simMgmtDtoNew.setBatchDate(fetchReadableDateTime(simMgmtDto.getBatchDate()));
            simMgmtDtoNew.setAllocationDate(fetchReadableDateTime(simMgmtDto.getAllocationDate()));
            simMgmtDtoNew.setSimType(simMgmtDto.getSimType());
            simMgmtDtoNew.setKeyId(simMgmtDto.getKeyId());
            simMgmtDtoNew.setAuthId(simMgmtDto.getAuthId());
            simMgmtDtoNew.setVendorName(simMgmtDto.getVendorName());
            simMgmtDtoNew.setStatus(simMgmtDto.getStatus());
            simMgmtDtoList.add(simMgmtDtoNew);
        }
        return simMgmtDtoList;
    }

    @Override
    public List<SimMgmtDtoNew> searchRecord(String keyword) {
        List<SimMgmt> simMgmtDbList = simMgmtRepository.searchItemsByName(keyword);
        List<SimMgmtDtoNew> simMgmtDtoList = new ArrayList<>();
        for (SimMgmt simMgmtDb : simMgmtDbList) {
            SimMgmtDtoNew simMgmtDto = new SimMgmtDtoNew();
            simMgmtDto.setSimId(simMgmtDb.getId());
            simMgmtDto.setImsi(simMgmtDb.getImsi());
            simMgmtDto.setBatchNo(simMgmtDb.getBatchNo());
            simMgmtDto.setBatchDate(fetchReadableDateTime(simMgmtDb.getBatchDate()));
            simMgmtDto.setAllocationDate(fetchReadableDateTime(simMgmtDb.getAllocationDate()));
            simMgmtDto.setSimType(simMgmtDb.getSimType());
            simMgmtDto.setKeyId(simMgmtDb.getKeyId());
            simMgmtDto.setAuthId(simMgmtDb.getAuthId());
            simMgmtDto.setVendorName(simMgmtDb.getVendorName());
            simMgmtDto.setStatus(simMgmtDb.getStatus());
            simMgmtDtoList.add(simMgmtDto);
        }
        return simMgmtDtoList;
    }

    private String fetchReadableDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
