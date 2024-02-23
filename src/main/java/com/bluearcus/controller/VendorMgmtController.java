package com.bluearcus.controller;

import com.bluearcus.dto.VendorMgmtDto;
import com.bluearcus.dto.VendorMgmtDtoNew;
import com.bluearcus.service.VendorMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/mgmt/detail")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/"}) 
public class VendorMgmtController {
    @Autowired
    private VendorMgmtService vendorMgmtService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<VendorMgmtDto> saveVendorMgmtDetails(@RequestBody VendorMgmtDtoNew vendorMgmtDto) {
        return vendorMgmtService.saveVendor(vendorMgmtDto);
    }

    @RequestMapping(value = "/edit/{vendor_id}", method = RequestMethod.PUT)
    public ResponseEntity<VendorMgmtDto> editVendorMgmtDetails(@PathVariable("vendor_id") Integer vendorId, @RequestBody VendorMgmtDto vendorMgmtDto) {
        return vendorMgmtService.editVendor(vendorId, vendorMgmtDto);
    }

    @RequestMapping(value = "/delete/{vendor_id}", method = RequestMethod.DELETE)
    public ResponseEntity<VendorMgmtDto> deleteVendorMgmtDetails(@PathVariable("vendor_id") Integer vendorId) {
        return vendorMgmtService.deleteVendor(vendorId);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<VendorMgmtDtoNew> getAllVendorMgmtDetails() {
        return vendorMgmtService.fetchAllVendors();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<VendorMgmtDtoNew> searchVendors(@RequestParam("keyword") String keyword) {
        return vendorMgmtService.searchVendors(keyword);
    }
}
