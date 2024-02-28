package com.bluearcus.controller;

import com.bluearcus.dto.DeviceMgmtDtoNew;
import com.bluearcus.service.DeviceMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device/mgmt/detail")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/","http://127.0.0.1:5500/","http://127.0.0.1:5174/","http://172.5.10.2:9091/"}) 
public class DeviceMgmtController {
    @Autowired
    private DeviceMgmtService deviceMgmtService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<DeviceMgmtDtoNew> saveDeviceMgmtDetails(@RequestBody DeviceMgmtDtoNew deviceMgmtDto) {
        return deviceMgmtService.saveDeviceMgmtDetail(deviceMgmtDto);
    }

    @RequestMapping(value = "/update/{device_id}", method = RequestMethod.PUT)
    public ResponseEntity<DeviceMgmtDtoNew> updateDeviceMgmtDetails(@PathVariable("device_id") Integer deviceId, @RequestBody DeviceMgmtDtoNew deviceMgmtDto) {
        return deviceMgmtService.editDeviceMgmtDetail(deviceId, deviceMgmtDto);
    }

    @RequestMapping(value = "/delete/{device_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDeviceMgmtDetails(@PathVariable("device_id") Integer deviceId) {
        return deviceMgmtService.deleteDeviceMgmtDetail(deviceId);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<DeviceMgmtDtoNew> getAllInventoryDetails() {
        return deviceMgmtService.fetchAllDeviceMgmtDetail();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<DeviceMgmtDtoNew>> searchInventoryDetails(@RequestParam("keyword") String keyword) {
        List<DeviceMgmtDtoNew> deviceMgmtList = deviceMgmtService.searchItems(keyword);
        return ResponseEntity.ok(deviceMgmtList);
    }
}
