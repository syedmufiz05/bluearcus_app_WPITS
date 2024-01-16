package com.bluearcus.service;

import com.bluearcus.dto.VendorMgmtDto;
import com.bluearcus.dto.VendorMgmtDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.VendorMgmt;
import com.bluearcus.repo.VendorMgmtRepository;
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
public class VendorMgmtServiceImpl implements VendorMgmtService {
    @Autowired
    private VendorMgmtRepository vendorMgmtRepository;

    @Override
    public ResponseEntity saveVendor(VendorMgmtDtoNew vendorMgmtDto) {
        VendorMgmt vendorMgmtDb = new VendorMgmt();
        vendorMgmtDb.setVendorName(vendorMgmtDto.getVendorName() != null ? vendorMgmtDto.getVendorName() : "");
        vendorMgmtDb.setEmail(vendorMgmtDto.getEmail() != null ? vendorMgmtDto.getEmail() : "");
        vendorMgmtDb.setContact(vendorMgmtDto.getContact() != null ? vendorMgmtDto.getContact() : "");
        vendorMgmtDb.setAddress(vendorMgmtDto.getAddress() != null ? vendorMgmtDto.getAddress() : "");
        vendorMgmtDb.setType(vendorMgmtDto.getType() != null ? vendorMgmtDto.getType() : "");
        vendorMgmtDb.setIdentification(vendorMgmtDto.getIdentification() != null ? vendorMgmtDto.getIdentification() : "");
        vendorMgmtDb.setBatchPrefix(vendorMgmtDto.getBatchPrefix() != null ? vendorMgmtDto.getBatchPrefix() : "");
        vendorMgmtDb.setStatus(vendorMgmtDto.getStatus() != null ? vendorMgmtDto.getStatus() : false);
        vendorMgmtRepository.save(vendorMgmtDb);
        VendorMgmtDtoNew vendorMgmtDtoNew = new VendorMgmtDtoNew(vendorMgmtDb.getId(), vendorMgmtDb.getVendorName(), vendorMgmtDb.getEmail(), vendorMgmtDb.getContact(), vendorMgmtDb.getAddress(), vendorMgmtDb.getType(), vendorMgmtDb.getIdentification(), vendorMgmtDb.getBatchPrefix(), fetchReadableDateTime(vendorMgmtDb.getRegistrationDate()), vendorMgmtDb.getStatus());
        return new ResponseEntity<>(vendorMgmtDtoNew, HttpStatus.OK);
    }

    @Override
    public ResponseEntity editVendor(Integer vendorId, VendorMgmtDto vendorMgmtDto) {
        Optional<VendorMgmt> vendorMgmt = vendorMgmtRepository.findById(vendorId);
        if (vendorMgmt.isPresent()) {
            VendorMgmt vendorMgmtDb = vendorMgmt.get();
            vendorMgmtDb.setVendorName(vendorMgmtDto.getVendorName() != null ? vendorMgmtDto.getVendorName() : vendorMgmtDb.getVendorName());
            vendorMgmtDb.setEmail(vendorMgmtDto.getEmail() != null ? vendorMgmtDto.getEmail() : vendorMgmtDb.getEmail());
            vendorMgmtDb.setContact(vendorMgmtDto.getContact() != null ? vendorMgmtDto.getContact() : vendorMgmtDb.getContact());
            vendorMgmtDb.setAddress(vendorMgmtDto.getAddress() != null ? vendorMgmtDto.getAddress() : vendorMgmtDb.getAddress());
            vendorMgmtDb.setType(vendorMgmtDto.getType() != null ? vendorMgmtDto.getType() : vendorMgmtDb.getType());
            vendorMgmtDb.setIdentification(vendorMgmtDto.getIdentification() != null ? vendorMgmtDto.getIdentification() : vendorMgmtDb.getIdentification());
            vendorMgmtDb.setBatchPrefix(vendorMgmtDto.getBatchPrefix() != null ? vendorMgmtDto.getBatchPrefix() : vendorMgmtDb.getBatchPrefix());
            vendorMgmtDb.setStatus(vendorMgmtDto.getStatus() != null ? vendorMgmtDto.getStatus() : vendorMgmtDb.getStatus());
            vendorMgmtRepository.save(vendorMgmtDb);
            VendorMgmtDtoNew vendorMgmtDtoNew = new VendorMgmtDtoNew(vendorMgmtDb.getId(), vendorMgmtDb.getVendorName(), vendorMgmtDb.getEmail(), vendorMgmtDb.getContact(), vendorMgmtDb.getAddress(), vendorMgmtDb.getType(), vendorMgmtDb.getIdentification(), vendorMgmtDb.getBatchPrefix(), fetchReadableDateTime(vendorMgmtDb.getRegistrationDate()), vendorMgmtDb.getStatus());
            return new ResponseEntity<>(vendorMgmtDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Vendor Id"));
    }

    @Transactional
    @Override
    public ResponseEntity deleteVendor(Integer vendorId) {
        Optional<VendorMgmt> vendorMgmt = vendorMgmtRepository.findById(vendorId);
        if (vendorMgmt.isPresent()) {
            vendorMgmtRepository.deleteById(vendorId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Vendor Id"));
    }

    @Override
    public List<VendorMgmtDtoNew> fetchAllVendors() {
        List<VendorMgmtDto> vendorMgmtDtoList = vendorMgmtRepository.fetchAllVendors();
        List<VendorMgmtDtoNew> vendorMgmtListDto = new ArrayList<>();
        for (VendorMgmtDto vendorMgmtDto : vendorMgmtDtoList) {
            VendorMgmtDtoNew vendorMgmtDtoNew = new VendorMgmtDtoNew();
            vendorMgmtDtoNew.setVendorId(vendorMgmtDto.getVendorId());
            vendorMgmtDtoNew.setVendorName(vendorMgmtDto.getVendorName());
            vendorMgmtDtoNew.setEmail(vendorMgmtDto.getEmail());
            vendorMgmtDtoNew.setContact(vendorMgmtDto.getContact());
            vendorMgmtDtoNew.setAddress(vendorMgmtDto.getAddress());
            vendorMgmtDtoNew.setType(vendorMgmtDto.getType());
            vendorMgmtDtoNew.setIdentification(vendorMgmtDto.getIdentification());
            vendorMgmtDtoNew.setBatchPrefix(vendorMgmtDto.getBatchPrefix());
            vendorMgmtDtoNew.setRegistrationDate(fetchReadableDateTime(vendorMgmtDto.getRegistrationDate()));
            vendorMgmtDtoNew.setStatus(vendorMgmtDto.getStatus());
            vendorMgmtListDto.add(vendorMgmtDtoNew);
        }
        return vendorMgmtListDto;
    }

    @Override
    public List<VendorMgmtDtoNew> searchVendors(String keyword) {
        List<VendorMgmt> vendorMgmtListDb = vendorMgmtRepository.searchItemsByName(keyword);
        List<VendorMgmtDtoNew> vendorMgmtListDto = new ArrayList<>();
        for (VendorMgmt vendorMgmtDb : vendorMgmtListDb) {
            VendorMgmtDtoNew vendorMgmtDto = new VendorMgmtDtoNew();
            vendorMgmtDto.setVendorId(vendorMgmtDb.getId());
            vendorMgmtDto.setVendorName(vendorMgmtDb.getVendorName());
            vendorMgmtDto.setEmail(vendorMgmtDb.getEmail());
            vendorMgmtDto.setContact(vendorMgmtDb.getContact());
            vendorMgmtDto.setAddress(vendorMgmtDb.getAddress());
            vendorMgmtDto.setType(vendorMgmtDb.getType());
            vendorMgmtDto.setIdentification(vendorMgmtDb.getIdentification());
            vendorMgmtDto.setBatchPrefix(vendorMgmtDb.getBatchPrefix());
            vendorMgmtDto.setRegistrationDate(fetchReadableDateTime(vendorMgmtDb.getRegistrationDate()));
            vendorMgmtDto.setStatus(vendorMgmtDb.getStatus());
            vendorMgmtListDto.add(vendorMgmtDto);
        }
        return vendorMgmtListDto;
    }

    private String fetchReadableDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
