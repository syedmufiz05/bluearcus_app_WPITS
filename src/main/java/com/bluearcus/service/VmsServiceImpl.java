package com.bluearcus.service;

import com.bluearcus.dto.VmsDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.AccessLogs;
import com.bluearcus.model.Vms;
import com.bluearcus.repo.AccessLogsRepository;
import com.bluearcus.repo.VmsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VmsServiceImpl implements VmsService {
    @Autowired
    private AccessLogsRepository accessLogsRepository;
    @Autowired
    private VmsRepository vmsRepository;

    @Override
    public ResponseEntity saveVmsDetails(VmsDto vmsDto, String authToken) throws JsonProcessingException {
        String msisdn = vmsDto.getMsisdn();
        Optional<Vms> vms = vmsRepository.findByMsisdn(msisdn);
        if (!vms.isPresent()) {
            AccessLogs accessLogs = new AccessLogs();
            accessLogs.setUserId(1212);
            accessLogs.setResponsePayload("");
            accessLogs.setAuthToken(authToken != null ? authToken : "");
            accessLogsRepository.save(accessLogs);
            Vms vmsDb = new Vms();
            vmsDb.setAccessLogs(accessLogs);
            vmsDb.setMsisdn(vmsDto.getMsisdn() != null ? vmsDto.getMsisdn() : "");
            vmsDb.setSystemId(vmsDto.getSystemId() != null ? vmsDto.getSystemId() : Integer.valueOf(""));
            vmsDb.setMailboxId(vmsDto.getMailboxId() != null ? vmsDto.getMailboxId() : Integer.valueOf(""));
            vmsDb.setRegisterFlag(vmsDto.getRegisterFlag() != null ? vmsDto.getRegisterFlag() : false);
            vmsDb.setActiveFlag(vmsDto.getActiveFlag() != null ? vmsDto.getActiveFlag() : false);
            vmsDb.setLockedFlag(vmsDto.getLockedFlag() != null ? vmsDto.getLockedFlag() : false);
            vmsDb.setLanguage(vmsDto.getLanguage() != null ? vmsDto.getLanguage() : Integer.valueOf(""));
            vmsDb.setTemporaryGreeting(vmsDto.getTemporaryGreeting() != null ? vmsDto.getTemporaryGreeting() : false);
            vmsDb.setGreetingTypeSystem(vmsDto.getGreetingTypeSystem() != null ? vmsDto.getGreetingTypeSystem() : "");
            vmsDb.setPasswordFlag(vmsDto.getPasswordFlag() != null ? vmsDto.getPasswordFlag() : false);
            vmsDb.setCallbackFlag(vmsDto.getCallbackFlag() != null ? vmsDto.getCallbackFlag() : false);
            vmsDb.setCliFlag(vmsDto.getCliFlag() != null ? vmsDto.getCliFlag() : false);
            vmsDb.setPassword(vmsDto.getPassword() != null ? vmsDto.getPassword() : "");
            vmsDb.setCallbackTimeout(vmsDto.getCallbackTimeout() != null ? vmsDto.getCallbackTimeout() : "");
            vmsRepository.save(vmsDb);
            saveVmsRequestPayload(vmsDb, vmsDto, accessLogs);
            VmsDto vmsDtoNew = new VmsDto(vmsDb.getVmsId(), vmsDb.getMsisdn(), vmsDb.getSystemId(), vmsDto.getMailboxId(), vmsDb.getRegisterFlag(), vmsDb.getActiveFlag(), vmsDb.getLockedFlag(), vmsDb.getLanguage(), vmsDb.getTemporaryGreeting(), vmsDb.getGreetingTypeSystem(), vmsDb.getPasswordFlag(), vmsDb.getCallbackFlag(), vmsDb.getCliFlag(), vmsDb.getPassword(), vmsDb.getCallbackTimeout(), vmsDb.getAccessLogs().getId());
            return new ResponseEntity<>(vmsDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Msisdn Id already exist"));
    }

    @Override
    public ResponseEntity getVmsDetails(String msisdn) {
        Optional<Vms> vms = vmsRepository.findByMsisdn(msisdn);
        if (vms.isPresent()) {
            Vms vmsDb = vms.get();
            VmsDto vmsDto = new VmsDto();
            vmsDto.setVmsId(vmsDb.getVmsId());
            vmsDto.setMsisdn(vmsDb.getMsisdn());
            vmsDto.setSystemId(vmsDb.getSystemId());
            vmsDto.setMailboxId(vmsDb.getMailboxId());
            vmsDto.setRegisterFlag(vmsDb.getRegisterFlag());
            vmsDto.setActiveFlag(vmsDb.getActiveFlag());
            vmsDto.setLockedFlag(vmsDb.getLockedFlag());
            vmsDto.setTemporaryGreeting(vmsDb.getTemporaryGreeting());
            vmsDto.setGreetingTypeSystem(vmsDb.getGreetingTypeSystem());
            vmsDto.setLanguage(vmsDb.getLanguage());
            vmsDto.setPasswordFlag(vmsDb.getPasswordFlag());
            vmsDto.setCallbackFlag(vmsDb.getCallbackFlag());
            vmsDto.setCliFlag(vmsDb.getCliFlag());
            vmsDto.setPassword(vmsDb.getPassword());
            vmsDto.setCallbackTimeout(vmsDb.getCallbackTimeout());
            vmsDto.setAccessId(vmsDb.getAccessLogs().getId());
            return new ResponseEntity<>(vmsDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Msisdn Id does n't exist"));
    }

    @Override
    public List<VmsDto> getAllVmsDetails() {
        return vmsRepository.fetchAllVmsRecord();
    }

    @Override
    public ResponseEntity updateVmsDetails(VmsDto vmsDto, String msisdn) throws JsonProcessingException {
        Optional<Vms> vms = vmsRepository.findByMsisdn(msisdn);
        if (vms.isPresent()) {
            Vms vmsDb = vms.get();
            vmsDb.setSystemId(vmsDto.getSystemId() != null ? vmsDto.getSystemId() : Integer.valueOf(""));
            vmsDb.setMailboxId(vmsDto.getMailboxId() != null ? vmsDto.getMailboxId() : Integer.valueOf(""));
            vmsDb.setRegisterFlag(vmsDto.getRegisterFlag() != null ? vmsDto.getRegisterFlag() : false);
            vmsDb.setActiveFlag(vmsDto.getActiveFlag() != null ? vmsDto.getActiveFlag() : false);
            vmsDb.setLockedFlag(vmsDto.getLockedFlag() != null ? vmsDto.getLockedFlag() : false);
            vmsDb.setLanguage(vmsDto.getLanguage() != null ? vmsDto.getLanguage() : Integer.valueOf(""));
            vmsDb.setTemporaryGreeting(vmsDto.getTemporaryGreeting() != null ? vmsDto.getTemporaryGreeting() : false);
            vmsDb.setGreetingTypeSystem(vmsDto.getGreetingTypeSystem() != null ? vmsDto.getGreetingTypeSystem() : "");
            vmsDb.setPasswordFlag(vmsDto.getPasswordFlag() != null ? vmsDto.getPasswordFlag() : false);
            vmsDb.setCallbackFlag(vmsDto.getCallbackFlag() != null ? vmsDto.getCallbackFlag() : false);
            vmsDb.setCliFlag(vmsDto.getCliFlag() != null ? vmsDto.getCliFlag() : false);
            vmsDb.setPassword(vmsDto.getPassword() != null ? vmsDto.getPassword() : "");
            vmsDb.setCallbackTimeout(vmsDto.getCallbackTimeout() != null ? vmsDto.getCallbackTimeout() : "");
            Optional<AccessLogs> accessLogsDb = accessLogsRepository.findById(vmsDb.getAccessLogs().getId());
            if (accessLogsDb.isPresent()) {
                AccessLogs accessLogs = accessLogsDb.get();
                vmsDto.setVmsId(vmsDb.getVmsId() != null ? vmsDb.getVmsId() : Integer.valueOf(""));
                vmsDto.setMsisdn(vmsDb.getMsisdn() != null ? vmsDb.getMsisdn() : "");
                vmsDto.setAccessId(accessLogs.getId());
                accessLogs.setReqPayload(convertEntityToJson(vmsDto));
                vmsRepository.save(vmsDb);
                accessLogsRepository.save(accessLogs);
            }
            VmsDto vmsDtoNew = new VmsDto(vmsDb.getVmsId(), vmsDb.getMsisdn(), vmsDb.getSystemId(), vmsDto.getMailboxId(), vmsDb.getRegisterFlag(), vmsDb.getActiveFlag(), vmsDb.getLockedFlag(), vmsDb.getLanguage(), vmsDb.getTemporaryGreeting(), vmsDb.getGreetingTypeSystem(), vmsDb.getPasswordFlag(), vmsDb.getCallbackFlag(), vmsDb.getCliFlag(), vmsDb.getPassword(), vmsDb.getCallbackTimeout(), vmsDb.getAccessLogs().getId());
            return new ResponseEntity<>(vmsDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Msisdn Id does n't exist"));
    }

    @Transactional
    @Override
    public String deleteVmsDetails(String msisdn) {
        vmsRepository.deleteByMsisdn(msisdn);
        return "VMS Details are deleted";
    }

    private void saveVmsRequestPayload(Vms vms, VmsDto vmsDto, AccessLogs accessLogs) throws JsonProcessingException {
        vmsDto.setVmsId(vms.getVmsId());
        vmsDto.setAccessId(vms.getAccessLogs().getId() != null ? vms.getAccessLogs().getId() : Integer.valueOf(""));
        String reqPayload = convertEntityToJson(vmsDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
    }

    private String convertEntityToJson(VmsDto vmsDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(vmsDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }

}
