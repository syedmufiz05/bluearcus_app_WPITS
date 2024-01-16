package com.bluearcus.service;

import com.bluearcus.dto.HssProvDto;
import com.bluearcus.dto.HssProvDtoNew;
import com.bluearcus.dto.SocketMsgDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.AccessLogs;
import com.bluearcus.model.HssProv;
import com.bluearcus.model.HssProvNew;
import com.bluearcus.repo.AccessLogsRepository;
import com.bluearcus.repo.HssProvRepository;
import com.bluearcus.repo.HssProvRepositoryNew;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HssProvServiceImpl implements HssProvService {
    @Autowired
    private HssProvRepository hssProvRepository;
    @Autowired
    private HssProvRepositoryNew hssProvRepositoryNew;
    @Autowired
    private AccessLogsRepository accessLogsRepository;
//    @Autowired
//    private SocketClient socketClient;
    @Autowired
    private HSSSocketClient hssSocketClient;


    public ResponseEntity saveHssProv(HssProvDto hssProvDto, String authToken) throws JsonProcessingException {
        String imsi = hssProvDto.getImsi();
        String msisdn = hssProvDto.getMsisdn();
        Optional<HssProv> hssProv = hssProvRepository.findByImsiOrMsisdn(imsi, msisdn);
        if (!hssProv.isPresent()) {
            AccessLogs accessLogs = new AccessLogs();
            accessLogs.setUserId(1212);
            accessLogs.setResponsePayload("");
            accessLogs.setAuthToken(authToken);
            accessLogsRepository.save(accessLogs);
            HssProv hssProvDb = new HssProv();
            hssProvDb.setImsi(hssProvDto.getImsi() != null ? hssProvDto.getImsi() : "");
            hssProvDb.setImsiFlag(hssProvDto.getImsiFlag() != null ? hssProvDto.getImsiFlag() : false);
            hssProvDb.setMsisdn(hssProvDto.getMsisdn() != null ? hssProvDto.getMsisdn() : "");
            hssProvDb.setNam(hssProvDto.getNam() != null ? hssProvDto.getNam() : "");
            hssProvDb.setOdb(hssProvDto.getOdb() != null ? hssProvDto.getOdb() : false);
            hssProvDb.setBaoc(hssProvDto.getBaoc() != null ? hssProvDto.getBaoc() : false);
            hssProvDb.setBoic(hssProvDto.getBoic() != null ? hssProvDto.getBoic() : false);
            hssProvDb.setOsb1(hssProvDto.getOsb1() != null ? hssProvDto.getOsb1() : false);
            hssProvDb.setOsb2(hssProvDto.getOsb2() != null ? hssProvDto.getOsb2() : false);
            hssProvDb.setBaic(hssProvDto.getBaic() != null ? hssProvDto.getBaic() : false);
            hssProvDb.setRoaming(hssProvDto.getRoaming() != null ? hssProvDto.getRoaming() : false);
            hssProvDb.setBearerService(hssProvDto.getBearerService() != null ? hssProvDto.getBearerService() : "");
            hssProvDb.setTelephone(hssProvDto.getTelephone() != null ? hssProvDto.getTelephone() : false);
            hssProvDb.setSms(hssProvDto.getSms() != null ? hssProvDto.getSms() : "");
            hssProvDb.setCfuA(hssProvDto.getCfuA() != null ? hssProvDto.getCfuA() : false);
            hssProvDb.setCfuR(hssProvDto.getCfuR() != null ? hssProvDto.getCfuR() : false);
            hssProvDb.setCfuP(hssProvDto.getCfuP() != null ? hssProvDto.getCfuP() : false);
            hssProvDb.setCfbP(hssProvDto.getCfbP() != null ? hssProvDto.getCfbP() : false);
            hssProvDb.setCfnryP(hssProvDto.getCfnryP() != null ? hssProvDto.getCfnryP() : false);
            hssProvDb.setCfnryT(hssProvDto.getCfnryT() != null ? hssProvDto.getCfnryT() : Integer.valueOf(""));
            hssProvDb.setCfnrcP(hssProvDto.getCfnrcP() != null ? hssProvDto.getCfnrcP() : false);
            hssProvDb.setCwA(hssProvDto.getCwA() != null ? hssProvDto.getCwA() : false);
            hssProvDb.setCwP(hssProvDto.getCwP() != null ? hssProvDto.getCwP() : false);
            hssProvDb.setChP(hssProvDto.getChP() != null ? hssProvDto.getChP() : false);
            hssProvDb.setCamel(hssProvDto.getCamel() != null ? hssProvDto.getCamel() : false);
            hssProvDb.setOCsi(hssProvDto.getOCsi() != null ? hssProvDto.getOCsi() : false);
            hssProvDb.setTCsi(hssProvDto.getTCsi() != null ? hssProvDto.getTCsi() : false);
            hssProvDb.setSsCsi(hssProvDto.getSsCsi() != null ? hssProvDto.getSsCsi() : false);
            hssProvDb.setSmsCsi(hssProvDto.getSmsCsi() != null ? hssProvDto.getSmsCsi() : false);
            hssProvDb.setOCsiScfNo(hssProvDto.getOCsiScfNo() != null ? hssProvDto.getOCsiScfNo() : "");
            hssProvDb.setTCsiScfNo(hssProvDto.getTCsiScfNo() != null ? hssProvDto.getTCsiScfNo() : "");
            hssProvDb.setSsCsiScfNo(hssProvDto.getSsCsiScfNo() != null ? hssProvDto.getSsCsiScfNo() : "");
            hssProvDb.setSmsSciScfNo(hssProvDto.getSmsSciScfNo() != null ? hssProvDto.getSmsSciScfNo() : "");
            hssProvDb.setGprsFlag(hssProvDto.getGprsFlag() != null ? hssProvDto.getGprsFlag() : false);
            hssProvDb.setEpsFlag(hssProvDto.getEpsFlag() != null ? hssProvDto.getEpsFlag() : false);
            hssProvDb.setArd(hssProvDto.getArd() != null ? hssProvDto.getArd() : "");
            hssProvDb.setEpsUserTpl(hssProvDto.getEpsUserTpl() != null ? hssProvDto.getEpsUserTpl() : "");
            hssProvDb.setDefEps(hssProvDto.getDefEps() != null ? hssProvDto.getDefEps() : "");
            hssProvDb.setContextD(hssProvDto.getContextD() != null ? hssProvDto.getContextD() : "");
            hssProvDb.setApnCtxtList(hssProvDto.getApnCtxtList() != null ? hssProvDto.getApnCtxtList() : "");
            hssProvDb.setImsFlag(hssProvDto.getImsFlag() != null ? hssProvDto.getImsFlag() : false);
            hssProvDb.setSubscriberProfId(hssProvDto.getSubscriberProfId() != null ? hssProvDto.getSubscriberProfId() : Integer.valueOf(""));
            hssProvDb.setAccessLogs(accessLogs);
            hssProvRepository.save(hssProvDb);
            saveAccessRequestPayload(hssProvDto, hssProvDb, accessLogs);
            HssProvDto hssProvDtoNew = new HssProvDto(hssProvDb.getHssprovId(), hssProvDb.getImsi(), hssProvDb.getImsiFlag(), hssProvDb.getMsisdn(), hssProvDb.getNam(), hssProvDb.getOdb(), hssProvDb.getBaoc(), hssProvDb.getBoic(), hssProvDb.getOsb1(), hssProvDb.getOsb2(), hssProvDb.getBaic(), hssProvDb.getRoaming(), hssProvDb.getBearerService(), hssProvDb.getTelephone(), hssProvDb.getSms(), hssProvDb.getCfuA(), hssProvDb.getCfuR(), hssProvDb.getCfuP(), hssProvDb.getCfbP(), hssProvDb.getCfnryP(), hssProvDb.getCfnryT(), hssProvDb.getCfnrcP(), hssProvDb.getCwA(), hssProvDb.getCwP(), hssProvDb.getChP(), hssProvDb.getCamel(), hssProvDb.getOCsi(), hssProvDb.getTCsi(), hssProvDb.getSsCsi(), hssProvDb.getSmsCsi(), hssProvDb.getOCsiScfNo(), hssProvDb.getTCsiScfNo(), hssProvDb.getSsCsiScfNo(), hssProvDb.getSmsSciScfNo(), hssProvDb.getGprsFlag(), hssProvDb.getEpsFlag(), hssProvDb.getArd(), hssProvDb.getEpsUserTpl(), hssProvDb.getDefEps(), hssProvDb.getContextD(), hssProvDb.getApnCtxtList(), hssProvDb.getImsFlag(), hssProvDb.getSubscriberProfId(), hssProvDb.getAccessLogs().getId());
            return new ResponseEntity<>(hssProvDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI or MSISDN Id already exist"));
    }

    @Override
    public ResponseEntity saveHssProvNew(HssProvDtoNew hssProvDtoNew, String authToken) {
        Optional<HssProvNew> hssProv = hssProvRepositoryNew.findById(hssProvDtoNew.getHssProvId() != null ? hssProvDtoNew.getHssProvId() : 0);
        if (!hssProv.isPresent()) {
            HssProvNew hssProvDb = new HssProvNew();
            hssProvDb.setImsi(hssProvDtoNew.getImsi());
            hssProvDb.setMsisdn(hssProvDtoNew.getMsisdn());
            hssProvDb.setAmbr(hssProvDtoNew.getAmbr());
            hssProvDb.setNssai(hssProvDtoNew.getNssai());
            hssProvDb.setArfb(hssProvDtoNew.getArfb());
            hssProvDb.setSar(hssProvDtoNew.getSar());
            hssProvDb.setRat(hssProvDtoNew.getRat());
            hssProvDb.setCn(hssProvDtoNew.getCn());
            hssProvDb.setSmfSel(hssProvDtoNew.getSmfSel());
            hssProvDb.setEpsFlag(hssProvDtoNew.getEpsFlag());
            hssProvDb.setEpsOdb(hssProvDtoNew.getEpsOdb());
            hssProvDb.setHplmnOdb(hssProvDtoNew.getHplmnOdb());
            hssProvDb.setArd(hssProvDtoNew.getArd());
            hssProvDb.setEpsTpl(hssProvDtoNew.getEpsTpl());
            hssProvDb.setContextId(hssProvDtoNew.getContextId());
            hssProvDb.setApnContext(hssProvDtoNew.getApnContext());
            hssProvDb.setSmDat(hssProvDtoNew.getSmDat());
            hssProvRepositoryNew.save(hssProvDb);
            HssProvDtoNew hssProvDto = new HssProvDtoNew(hssProvDb.getId(), hssProvDb.getImsi(), hssProvDb.getMsisdn(), hssProvDb.getAmbr(), hssProvDb.getNssai(), hssProvDb.getArfb(), hssProvDb.getSar(), hssProvDb.getRat(), hssProvDb.getCn(), hssProvDb.getSmfSel(), hssProvDb.getSmDat(), hssProvDb.getEpsFlag(), hssProvDb.getEpsOdb(), hssProvDb.getHplmnOdb(), hssProvDb.getArd(), hssProvDb.getEpsTpl(), hssProvDb.getContextId(), hssProvDb.getApnContext());
            return new ResponseEntity<>(hssProvDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Duplicate HssProv Id"));
    }

    @Override
    public List<HssProvDto> getAllHssProvRecord() {
        List<HssProvDto> hssProvDtoList = hssProvRepository.fetchAllHssProvRecord();
        return hssProvDtoList;
    }

    @Override
    public ResponseEntity getHssProv(String imsi, String msisdn) {
        // Optional<HssProv> hssProv = hssProvRepository.findByImsiOrMsisdn(imsi, msisdn);
        Optional<HssProvNew> hssProvNew = hssProvRepositoryNew.findByImsiOrMsisdn(imsi, msisdn);
        if (hssProvNew.isPresent()) {
            HssProvNew hssProvDb = hssProvNew.get();
//            socketClient.connect();
//            String msg = setSocketMsgBody(hssProvDb);
//            socketClient.sendCommand(msg);
//            socketClient.logout();
        }
//        if (hssProv.isPresent()) {
//            HssProv hssProvDb = hssProv.get();
//            // Sending data to the socket....
//            String msg = setSocketMsgBody(hssProvDb);
//            hssSocketClient.sendMessage(msg);
//
//            HssProvDto hssProvDto = new HssProvDto();
//            hssProvDto.setHssProvId(hssProvDb.getHssprovId());
//            hssProvDto.setImsi(hssProvDb.getImsi());
//            hssProvDto.setImsiFlag(hssProvDb.getImsiFlag());
//            hssProvDto.setMsisdn(hssProvDb.getMsisdn());
//            hssProvDto.setNam(hssProvDb.getNam());
//            hssProvDto.setOdb(hssProvDb.getOdb());
//            hssProvDto.setBaoc(hssProvDb.getBaoc());
//            hssProvDto.setBoic(hssProvDb.getBoic());
//            hssProvDto.setOsb1(hssProvDb.getOsb1());
//            hssProvDto.setOsb2(hssProvDb.getOsb2());
//            hssProvDto.setBaic(hssProvDb.getBaic());
//            hssProvDto.setRoaming(hssProvDb.getRoaming());
//            hssProvDto.setBearerService(hssProvDb.getBearerService());
//            hssProvDto.setTelephone(hssProvDb.getTelephone());
//            hssProvDto.setSms(hssProvDb.getSms());
//            hssProvDto.setCfuA(hssProvDb.getCfuA());
//            hssProvDto.setCfuR(hssProvDb.getCfuR());
//            hssProvDto.setCfuP(hssProvDb.getCfuP());
//            hssProvDto.setCfbP(hssProvDb.getCfbP());
//            hssProvDto.setCfnryP(hssProvDb.getCfnryP());
//            hssProvDto.setCfnryT(hssProvDb.getCfnryT());
//            hssProvDto.setCfnrcP(hssProvDb.getCfnrcP());
//            hssProvDto.setCwA(hssProvDb.getCwA());
//            hssProvDto.setCwP(hssProvDb.getCwP());
//            hssProvDto.setChP(hssProvDb.getChP());
//            hssProvDto.setCamel(hssProvDb.getCamel());
//            hssProvDto.setOCsi(hssProvDb.getOCsi());
//            hssProvDto.setTCsi(hssProvDb.getTCsi());
//            hssProvDto.setSsCsi(hssProvDb.getSsCsi());
//            hssProvDto.setSmsCsi(hssProvDb.getSmsCsi());
//            hssProvDto.setOCsiScfNo(hssProvDb.getOCsiScfNo());
//            hssProvDto.setTCsiScfNo(hssProvDb.getTCsiScfNo());
//            hssProvDto.setSsCsiScfNo(hssProvDb.getSsCsiScfNo());
//            hssProvDto.setSmsSciScfNo(hssProvDb.getSmsSciScfNo());
//            hssProvDto.setGprsFlag(hssProvDb.getGprsFlag());
//            hssProvDto.setEpsFlag(hssProvDb.getEpsFlag());
//            hssProvDto.setArd(hssProvDb.getArd());
//            hssProvDto.setEpsUserTpl(hssProvDb.getEpsUserTpl());
//            hssProvDto.setDefEps(hssProvDb.getDefEps());
//            hssProvDto.setContextD(hssProvDb.getContextD());
//            hssProvDto.setApnCtxtList(hssProvDb.getApnCtxtList());
//            hssProvDto.setImsFlag(hssProvDb.getImsFlag());
//            hssProvDto.setSubscriberProfId(hssProvDb.getSubscriberProfId());
//            hssProvDto.setAccessId(hssProvDb.getAccessLogs().getId());
//            return new ResponseEntity<>(hssProvDto, HttpStatus.OK);
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "IMSI or MSISDN Id does n't exist"));
    }

    @Transactional
    public ResponseEntity updateHssProv(String imsi, String msisdn, HssProvDto hssProvDto) throws JsonProcessingException {
        Optional<HssProv> hssProv = hssProvRepository.findByImsiOrMsisdn(imsi, msisdn);
        if (hssProv.isPresent()) {
            HssProv hssProvDb = hssProv.get();
            hssProvDb.setImsiFlag(hssProvDto.getImsiFlag() != null ? hssProvDto.getImsiFlag() : false);
            hssProvDb.setMsisdn(hssProvDto.getMsisdn() != null ? hssProvDto.getMsisdn() : "");
            hssProvDb.setNam(hssProvDto.getNam() != null ? hssProvDto.getNam() : "");
            hssProvDb.setOdb(hssProvDto.getOdb() != null ? hssProvDto.getOdb() : false);
            hssProvDb.setBaoc(hssProvDto.getBaoc() != null ? hssProvDto.getBaoc() : false);
            hssProvDb.setBoic(hssProvDto.getBoic() != null ? hssProvDto.getBoic() : false);
            hssProvDb.setOsb1(hssProvDto.getOsb1() != null ? hssProvDto.getOsb1() : false);
            hssProvDb.setOsb2(hssProvDto.getOsb2() != null ? hssProvDto.getOsb2() : false);
            hssProvDb.setBaic(hssProvDto.getBaic() != null ? hssProvDto.getBaic() : false);
            hssProvDb.setRoaming(hssProvDto.getRoaming() != null ? hssProvDto.getRoaming() : false);
            hssProvDb.setBearerService(hssProvDto.getBearerService() != null ? hssProvDto.getBearerService() : "");
            hssProvDb.setTelephone(hssProvDto.getTelephone() != null ? hssProvDto.getTelephone() : false);
            hssProvDb.setSms(hssProvDto.getSms() != null ? hssProvDto.getSms() : "");
            hssProvDb.setCfuA(hssProvDto.getCfuA() != null ? hssProvDto.getCfuA() : false);
            hssProvDb.setCfuR(hssProvDto.getCfuR() != null ? hssProvDto.getCfuR() : false);
            hssProvDb.setCfuP(hssProvDto.getCfuP() != null ? hssProvDto.getCfuP() : false);
            hssProvDb.setCfbP(hssProvDto.getCfbP() != null ? hssProvDto.getCfbP() : false);
            hssProvDb.setCfnryP(hssProvDto.getCfnryP() != null ? hssProvDto.getCfnryP() : false);
            hssProvDb.setCfnryT(hssProvDto.getCfnryT() != null ? hssProvDto.getCfnryT() : Integer.valueOf(""));
            hssProvDb.setCfnrcP(hssProvDto.getCfnrcP() != null ? hssProvDto.getCfnrcP() : false);
            hssProvDb.setCwA(hssProvDto.getCwA() != null ? hssProvDto.getCwA() : false);
            hssProvDb.setCwP(hssProvDto.getCwP() != null ? hssProvDto.getCwP() : false);
            hssProvDb.setChP(hssProvDto.getChP() != null ? hssProvDto.getChP() : false);
            hssProvDb.setCamel(hssProvDto.getCamel() != null ? hssProvDto.getCamel() : false);
            hssProvDb.setOCsi(hssProvDto.getOCsi() != null ? hssProvDto.getOCsi() : false);
            hssProvDb.setTCsi(hssProvDto.getTCsi() != null ? hssProvDto.getTCsi() : false);
            hssProvDb.setSsCsi(hssProvDto.getSsCsi() != null ? hssProvDto.getSsCsi() : false);
            hssProvDb.setSmsCsi(hssProvDto.getSmsCsi() != null ? hssProvDto.getSmsCsi() : false);
            hssProvDb.setOCsiScfNo(hssProvDto.getOCsiScfNo() != null ? hssProvDto.getOCsiScfNo() : "");
            hssProvDb.setTCsiScfNo(hssProvDto.getTCsiScfNo() != null ? hssProvDto.getTCsiScfNo() : "");
            hssProvDb.setSsCsiScfNo(hssProvDto.getSsCsiScfNo() != null ? hssProvDto.getSsCsiScfNo() : "");
            hssProvDb.setSmsSciScfNo(hssProvDto.getSmsSciScfNo() != null ? hssProvDto.getSmsSciScfNo() : "");
            hssProvDb.setGprsFlag(hssProvDto.getGprsFlag() != null ? hssProvDto.getGprsFlag() : false);
            hssProvDb.setEpsFlag(hssProvDto.getEpsFlag() != null ? hssProvDto.getEpsFlag() : false);
            hssProvDb.setArd(hssProvDto.getArd() != null ? hssProvDto.getArd() : "");
            hssProvDb.setEpsUserTpl(hssProvDto.getEpsUserTpl() != null ? hssProvDto.getEpsUserTpl() : "");
            hssProvDb.setDefEps(hssProvDto.getDefEps() != null ? hssProvDto.getDefEps() : "");
            hssProvDb.setContextD(hssProvDto.getContextD() != null ? hssProvDto.getContextD() : "");
            hssProvDb.setApnCtxtList(hssProvDto.getApnCtxtList() != null ? hssProvDto.getApnCtxtList() : "");
            hssProvDb.setImsFlag(hssProvDto.getImsFlag() != null ? hssProvDto.getImsFlag() : false);
            hssProvDb.setSubscriberProfId(hssProvDto.getSubscriberProfId() != null ? hssProvDto.getSubscriberProfId() : Integer.valueOf(""));
            Optional<AccessLogs> accessLogsDb = accessLogsRepository.findById(hssProvDb.getAccessLogs().getId());
            AccessLogs accessLogs = accessLogsDb.get();
            accessLogs.setAccessDateTime(new Date());
            accessLogs.setReqPayload(updateAccessRequestPayload(hssProvDto, hssProvDb, accessLogs));
            accessLogsRepository.save(accessLogs);
            hssProvRepository.save(hssProvDb);
            HssProvDto hssProvDtoNew = new HssProvDto(hssProvDb.getHssprovId(), hssProvDb.getImsi(), hssProvDb.getImsiFlag(), hssProvDb.getMsisdn(), hssProvDb.getNam(), hssProvDb.getOdb(), hssProvDb.getBaoc(), hssProvDb.getBoic(), hssProvDb.getOsb1(), hssProvDb.getOsb2(), hssProvDb.getBaic(), hssProvDb.getRoaming(), hssProvDb.getBearerService(), hssProvDb.getTelephone(), hssProvDb.getSms(), hssProvDb.getCfuA(), hssProvDb.getCfuR(), hssProvDb.getCfuP(), hssProvDb.getCfbP(), hssProvDb.getCfnryP(), hssProvDb.getCfnryT(), hssProvDb.getCfnrcP(), hssProvDb.getCwA(), hssProvDb.getCwP(), hssProvDb.getChP(), hssProvDb.getCamel(), hssProvDb.getOCsi(), hssProvDb.getTCsi(), hssProvDb.getSsCsi(), hssProvDb.getSmsCsi(), hssProvDb.getOCsiScfNo(), hssProvDb.getTCsiScfNo(), hssProvDb.getSsCsiScfNo(), hssProvDb.getSmsSciScfNo(), hssProvDb.getGprsFlag(), hssProvDb.getEpsFlag(), hssProvDb.getArd(), hssProvDb.getEpsUserTpl(), hssProvDb.getDefEps(), hssProvDb.getContextD(), hssProvDb.getApnCtxtList(), hssProvDb.getImsFlag(), hssProvDb.getSubscriberProfId(), hssProvDb.getAccessLogs().getId());
            return new ResponseEntity<>(hssProvDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "IMSI or MSISDN Id does n't exist"));
    }

    @Transactional
    public String deleteHssProv(String imsi, String msisdn) {
        hssProvRepository.deleteByImsiOrMsisdn(imsi, msisdn);
        return "Successfully deleted...";
    }

    private void saveAccessRequestPayload(HssProvDto hssProvDto, HssProv hssProv, AccessLogs accessLogs) throws JsonProcessingException {
        hssProvDto.setHssProvId(hssProv.getHssprovId());
        hssProvDto.setAccessId(hssProv.getAccessLogs().getId() != null ? hssProv.getAccessLogs().getId() : Integer.valueOf(""));
        String reqPayload = convertEntityToJson(hssProvDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
        SocketMsgDto socketMsgDto = new SocketMsgDto(hssProvDto.getImsi(), hssProvDto.getImsiFlag(), hssProvDto.getMsisdn(), hssProvDto.getNam(), hssProvDto.getOdb(), hssProvDto.getBaoc(), hssProvDto.getBoic(), hssProvDto.getOsb1(), hssProvDto.getOsb2(), hssProvDto.getBaic(), hssProvDto.getRoaming(), hssProvDto.getBearerService(), hssProvDto.getTelephone(), hssProvDto.getSms(), hssProvDto.getCfuA(), hssProvDto.getCfuR(), hssProvDto.getCfuP(), hssProvDto.getCfbP(), hssProvDto.getCfnryP(), hssProvDto.getCfnryT(), hssProvDto.getCfnrcP(), hssProvDto.getCwA(), hssProvDto.getCwP(), hssProvDto.getChP(), hssProvDto.getCamel(), hssProvDto.getOCsi(), hssProvDto.getTCsi(), hssProvDto.getSsCsi(), hssProvDto.getSmsCsi(), hssProvDto.getOCsiScfNo(), hssProvDto.getTCsiScfNo(), hssProvDto.getSsCsiScfNo(), hssProvDto.getSmsSciScfNo(), hssProvDto.getGprsFlag(), hssProvDto.getEpsFlag(), hssProvDto.getArd(), hssProvDto.getEpsUserTpl(), hssProvDto.getDefEps(), hssProvDto.getContextD(), hssProvDto.getApnCtxtList(), hssProvDto.getImsFlag());
        String reqMsg = convertDtoToJson(socketMsgDto);
        //  hssSocketClient.sendMessage(reqMsg);
    }

    private String updateAccessRequestPayload(HssProvDto hssProvDto, HssProv hssProvDb, AccessLogs accessLogs) throws JsonProcessingException {
        hssProvDto.setHssProvId(hssProvDb.getHssprovId());
        hssProvDto.setDefEps(hssProvDb.getDefEps() != null ? hssProvDb.getDefEps() : "");
        hssProvDto.setAccessId(accessLogs.getId() != null ? accessLogs.getId() : Integer.valueOf(""));
        String reqPayload = convertEntityToJson(hssProvDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
        return reqPayload;
    }

    private static String convertEntityToJson(HssProvDto hssProvDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(hssProvDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }

    private static String convertDtoToJson(SocketMsgDto socketMsgDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(socketMsgDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }

    private static String setSocketMsgBody(HssProvNew hssProvNew) {
        String body = "add udmuser:imsi=" + hssProvNew.getImsi() + ",msisdn=" + hssProvNew.getMsisdn() + ",ambr=" + hssProvNew.getAmbr() + ",nssai=" + hssProvNew.getNssai() + ",arfb=" + hssProvNew.getArfb() + ",sar=" + hssProvNew.getSar() + ",rat=" + hssProvNew.getRat() + ",cn=" + hssProvNew.getCn() + ",smf_sel=" + hssProvNew.getSmfSel() + ",sm_dat=" + hssProvNew.getSmDat() + ",eps_flag=" + hssProvNew.getEpsFlag() + ",eps_odb=" + hssProvNew.getEpsOdb() + ",hplmn_odb=" + hssProvNew.getHplmnOdb() + ",ard=" + hssProvNew.getArd() + ",epstpl=" + hssProvNew.getEpsTpl() + ",context_id=" + hssProvNew.getContextId() + ",apn_context=" + hssProvNew.getApnContext();
        return body;
    }

}
