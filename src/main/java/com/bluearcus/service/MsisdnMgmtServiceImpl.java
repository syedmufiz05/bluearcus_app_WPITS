package com.bluearcus.service;

import com.bluearcus.dto.MsisdnMgmtDto;
import com.bluearcus.dto.MsisdnMgmtDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.MsisdnMgmt;
import com.bluearcus.repo.MsisdnMgmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MsisdnMgmtServiceImpl implements MsisdnMgmtService {
    @Autowired
    private MsisdnMgmtRepository msisdnMgmtRepository;

    @Override
    public ResponseEntity saveMsisdnMgmt(MsisdnMgmtDtoNew msisdnMgmtDto) {
        Optional<MsisdnMgmt> msisdnMgmt = msisdnMgmtRepository.findByMsisdn(msisdnMgmtDto.getMsisdn());
        if (!msisdnMgmt.isPresent()) {
            MsisdnMgmt msisdnMgmtDb = new MsisdnMgmt();
            msisdnMgmtDb.setMsisdn(msisdnMgmtDto.getMsisdn() != null ? msisdnMgmtDto.getMsisdn() : "");
            msisdnMgmtDb.setCategory(msisdnMgmtDto.getCategory() != null ? msisdnMgmtDto.getCategory() : "");
            msisdnMgmtDb.setSeriesId(msisdnMgmtDto.getSeriesId() != null ? msisdnMgmtDto.getSeriesId() : Integer.valueOf(""));
            msisdnMgmtDb.setIsPrepaid(msisdnMgmtDto.getIsPrepaid() != null ? msisdnMgmtDto.getIsPrepaid() : false);
            msisdnMgmtDb.setIsPostpaid(msisdnMgmtDto.getIsPostpaid() != null ? msisdnMgmtDto.getIsPostpaid() : false);
            msisdnMgmtDb.setIsM2M(msisdnMgmtDto.getIsM2M() != null ? msisdnMgmtDto.getIsM2M() : false);
            msisdnMgmtDb.setIsSpecialNo(msisdnMgmtDto.getIsSpecialNo() != null ? msisdnMgmtDto.getIsSpecialNo() : false);
            msisdnMgmtDb.setStatus(msisdnMgmtDto.getStatus() != null ? msisdnMgmtDto.getStatus() : false);
            msisdnMgmtRepository.save(msisdnMgmtDb);
            MsisdnMgmtDtoNew msisdnMgmtDtoNew = new MsisdnMgmtDtoNew(msisdnMgmtDb.getId(), msisdnMgmtDb.getMsisdn(), msisdnMgmtDb.getCategory(), msisdnMgmtDb.getSeriesId(), msisdnMgmtDb.getIsPrepaid(), msisdnMgmtDb.getIsPostpaid(), msisdnMgmtDb.getIsM2M(), msisdnMgmtDb.getIsSpecialNo(), fetchReadableDateTime(msisdnMgmtDb.getAllocationDate()), msisdnMgmtDb.getStatus());
            return new ResponseEntity<>(msisdnMgmtDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "MSISDN already exist"));
    }

    @Override
    public ResponseEntity editMsisdnMgmt(Integer msisdnId, MsisdnMgmtDtoNew msisdnMgmtDto) {
        Optional<MsisdnMgmt> msisdnMgmt = msisdnMgmtRepository.findById(msisdnId);
        if (msisdnMgmt.isPresent()) {
            MsisdnMgmt msisdnMgmtDb = msisdnMgmt.get();
            msisdnMgmtDb.setMsisdn(msisdnMgmtDto.getMsisdn() != null ? msisdnMgmtDto.getMsisdn() : msisdnMgmtDb.getMsisdn());
            msisdnMgmtDb.setCategory(msisdnMgmtDto.getCategory() != null ? msisdnMgmtDto.getCategory() : msisdnMgmtDb.getCategory());
            msisdnMgmtDb.setSeriesId(msisdnMgmtDto.getSeriesId() != null ? msisdnMgmtDto.getSeriesId() : msisdnMgmtDb.getSeriesId());
            msisdnMgmtDb.setIsPrepaid(msisdnMgmtDto.getIsPrepaid() != null ? msisdnMgmtDto.getIsPrepaid() : msisdnMgmtDb.getIsPrepaid());
            msisdnMgmtDb.setIsPostpaid(msisdnMgmtDto.getIsPostpaid() != null ? msisdnMgmtDto.getIsPostpaid() : msisdnMgmtDb.getIsPostpaid());
            msisdnMgmtDb.setIsM2M(msisdnMgmtDto.getIsM2M() != null ? msisdnMgmtDto.getIsM2M() : msisdnMgmtDb.getIsM2M());
            msisdnMgmtDb.setIsSpecialNo(msisdnMgmtDto.getIsSpecialNo() != null ? msisdnMgmtDto.getIsSpecialNo() : msisdnMgmtDb.getIsSpecialNo());
            msisdnMgmtDb.setStatus(msisdnMgmtDto.getStatus() != null ? msisdnMgmtDto.getStatus() : msisdnMgmtDb.getStatus());
            msisdnMgmtRepository.save(msisdnMgmtDb);
            MsisdnMgmtDtoNew msisdnMgmtDtoNew = new MsisdnMgmtDtoNew(msisdnMgmtDb.getId(), msisdnMgmtDb.getMsisdn(), msisdnMgmtDb.getCategory(), msisdnMgmtDb.getSeriesId(), msisdnMgmtDb.getIsPrepaid(), msisdnMgmtDb.getIsPostpaid(), msisdnMgmtDb.getIsM2M(), msisdnMgmtDb.getIsSpecialNo(), fetchReadableDateTime(msisdnMgmtDb.getAllocationDate()), msisdnMgmtDb.getStatus());
            return new ResponseEntity<>(msisdnMgmtDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid MSISDN Id"));
    }

    @Transactional
    @Override
    public ResponseEntity deleteMsisdnMgmt(Integer msisdnId) {
        Optional<MsisdnMgmt> msisdnMgmt = msisdnMgmtRepository.findById(msisdnId);
        if (msisdnMgmt.isPresent()) {
            msisdnMgmtRepository.deleteById(msisdnId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.OK.value(), "Invalid MSISDN Id"));
    }

    @Override
    public List<MsisdnMgmtDtoNew> getAllMsisdnDetail() {
        List<MsisdnMgmtDto> msisdnMgmtDbList = msisdnMgmtRepository.fetchAllMsisdnMgmtRecord();
        List<MsisdnMgmtDtoNew> msisdnMgmtDtoList = new ArrayList<>();
        for (MsisdnMgmtDto msisdnMgmt : msisdnMgmtDbList) {
            MsisdnMgmtDtoNew msisdnMgmtDto = new MsisdnMgmtDtoNew();
            msisdnMgmtDto.setId(msisdnMgmt.getId());
            msisdnMgmtDto.setMsisdn(msisdnMgmt.getMsisdn());
            msisdnMgmtDto.setCategory(msisdnMgmt.getCategory());
            msisdnMgmtDto.setSeriesId(msisdnMgmt.getSeriesId());
            msisdnMgmtDto.setIsPrepaid(msisdnMgmt.getIsPrepaid());
            msisdnMgmtDto.setIsPostpaid(msisdnMgmt.getIsPostpaid());
            msisdnMgmtDto.setIsM2M(msisdnMgmt.getIsM2M());
            msisdnMgmtDto.setIsSpecialNo(msisdnMgmt.getIsSpecialNo());
            msisdnMgmtDto.setAllocationDate(fetchReadableDateTime(msisdnMgmt.getAllocationDate()));
            msisdnMgmtDto.setStatus(msisdnMgmt.getStatus());
            msisdnMgmtDtoList.add(msisdnMgmtDto);
        }
        return msisdnMgmtDtoList;
    }

    @Override
    public List<MsisdnMgmtDtoNew> searchRecord(String keyword) {
        List<MsisdnMgmt> msisdnMgmtListDb = msisdnMgmtRepository.searchItemsByName(keyword);
        List<MsisdnMgmtDtoNew> msisdnMgmtDtoList = new ArrayList<>();
        for (MsisdnMgmt msisdnMgmt : msisdnMgmtListDb) {
            MsisdnMgmtDtoNew msisdnMgmtDto = new MsisdnMgmtDtoNew();
            msisdnMgmtDto.setId(msisdnMgmt.getId());
            msisdnMgmtDto.setMsisdn(msisdnMgmt.getMsisdn());
            msisdnMgmtDto.setCategory(msisdnMgmt.getCategory());
            msisdnMgmtDto.setSeriesId(msisdnMgmt.getSeriesId());
            msisdnMgmtDto.setIsPrepaid(msisdnMgmt.getIsPrepaid());
            msisdnMgmtDto.setIsPostpaid(msisdnMgmt.getIsPostpaid());
            msisdnMgmtDto.setIsM2M(msisdnMgmt.getIsM2M());
            msisdnMgmtDto.setIsSpecialNo(msisdnMgmt.getIsSpecialNo());
            msisdnMgmtDto.setAllocationDate(fetchReadableDateTime(msisdnMgmt.getAllocationDate()));
            msisdnMgmtDto.setStatus(msisdnMgmt.getStatus());
            msisdnMgmtDtoList.add(msisdnMgmtDto);
        }
        return msisdnMgmtDtoList;
    }

    private String fetchReadableDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
