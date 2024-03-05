package com.bluearcus.controller;

import com.bluearcus.dto.SimMgmtDtoNew;
import com.bluearcus.service.SimMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sim/mgmt/detail")
@CrossOrigin("*") 
public class SimMgmtController {
    @Autowired
    private SimMgmtService simMgmtService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<SimMgmtDtoNew> saveSimMgmtDetails(@RequestBody SimMgmtDtoNew simMgmtDto) {
        return simMgmtService.saveSimMgmt(simMgmtDto);
    }

    @RequestMapping(value = "/edit/{sim_id}", method = RequestMethod.PUT)
    public ResponseEntity<SimMgmtDtoNew> editSimMgmtDetails(@PathVariable("sim_id") Integer simId, @RequestBody SimMgmtDtoNew simMgmtDto) {
        return simMgmtService.editSimMgmt(simId, simMgmtDto);
    }

    @RequestMapping(value = "/delete/{sim_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSimMgmtDetails(@PathVariable("sim_id") Integer simId) {
        return simMgmtService.deleteSimMgmt(simId);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<SimMgmtDtoNew> getAllSimMgmtDetails() {
        return simMgmtService.getAllSimMgmt();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<SimMgmtDtoNew> getSearchRecords(@RequestParam("keyword") String keyword) {
        return simMgmtService.searchRecord(keyword);
    }
}
