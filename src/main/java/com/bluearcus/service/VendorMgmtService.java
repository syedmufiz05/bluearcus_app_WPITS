package com.bluearcus.service;

import com.bluearcus.dto.VendorMgmtDto;
import com.bluearcus.dto.VendorMgmtDtoNew;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendorMgmtService {
    ResponseEntity saveVendor(VendorMgmtDtoNew vendorMgmtDto);

    ResponseEntity editVendor(Integer vendorId, VendorMgmtDto vendorMgmtDto);

    ResponseEntity deleteVendor(Integer vendorId);

    List<VendorMgmtDtoNew> fetchAllVendors();

    List<VendorMgmtDtoNew> searchVendors(String keyword);
}
