package com.bluearcus.controller;

import com.bluearcus.dto.PrepaidRoamingAccountsDto;
import com.bluearcus.service.PrepaidRoamingAccountsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prepaid/roaming/account")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/","http://127.0.0.1:5500/","http://127.0.0.1:5174/","http://172.5.10.2:9091/"}) 
public class PrepaidRoamingAccountController {
    @Autowired
    private PrepaidRoamingAccountsService prepaidRoamingAccountsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<PrepaidRoamingAccountsDto> savePrepaidRoamingAccount(@RequestBody PrepaidRoamingAccountsDto prepaidRoamingAccountsDto) {
        return prepaidRoamingAccountsService.savePrepaidRoamingAccount(prepaidRoamingAccountsDto);
    }

    @RequestMapping(value = "/edit/{roaming_account_id}", method = RequestMethod.PUT)
    public ResponseEntity<PrepaidRoamingAccountsDto> editPrepaidRoamingAccount(@PathVariable("roaming_account_id") Integer roamingAccountId, @RequestBody PrepaidRoamingAccountsDto prepaidRoamingAccountsDto) {
        return prepaidRoamingAccountsService.editPrepaidRoamingAccount(roamingAccountId, prepaidRoamingAccountsDto);
    }

    @RequestMapping(value = "/delete/{roaming_account_id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePrepaidRoamingAccount(@PathVariable("roaming_account_id") Integer roamingAccountId) {
        return prepaidRoamingAccountsService.deletePrepaidRoamingAccount(roamingAccountId);
    }

    @RequestMapping(value = "/get/{roaming_account_id}", method = RequestMethod.GET)
    public ResponseEntity<PrepaidRoamingAccountsDto> getPrepaidRoamingAccount(@PathVariable("roaming_account_id") Integer roamingAccountId) {
        return prepaidRoamingAccountsService.getPrepaidRoamingAccount(roamingAccountId);
    }
    
    @RequestMapping(value ="/get/all",method = RequestMethod.GET)
    public List<PrepaidRoamingAccountsDto> getAllPrepaidRoamingAccount(){
    	return prepaidRoamingAccountsService.getAllPrepaidRoamingAccounts();
    }
}

