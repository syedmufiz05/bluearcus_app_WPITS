package com.bluearcus.service;

import com.bluearcus.dto.HssProvDtoNew;
import com.bluearcus.dto.SocketMsgDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.AccessLogs;
import com.bluearcus.model.HssProvNew;
import com.bluearcus.repo.AccessLogsRepository;
import com.bluearcus.repo.HssProvRepositoryNew;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HssProvServiceImpl implements HssProvService {

	@Autowired
	private HssProvRepositoryNew hssProvRepositoryNew;

	@Autowired
	private AccessLogsRepository accessLogsRepository;

//	@Autowired
//	private SocketClient socketClient;

    @Override
    public ResponseEntity saveHssProvNew(HssProvDtoNew hssProvDtoNew, String authToken) throws JsonProcessingException {
		Optional<HssProvNew> hssProv = hssProvRepositoryNew.findByImsiOrMsisdn(hssProvDtoNew.getImsi(), hssProvDtoNew.getMsisdn());
        if (!hssProv.isPresent()) {
        	AccessLogs accessLogs = new AccessLogs();
            accessLogs.setUserId(1212);
            accessLogs.setResponsePayload("");
            accessLogs.setAuthToken(authToken);
            accessLogsRepository.save(accessLogs);
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
            hssProvDb.setAccessLogs(accessLogs);
            hssProvRepositoryNew.save(hssProvDb);
            saveAccessRequestPayload(hssProvDtoNew, hssProvDb, accessLogs);
            HssProvDtoNew hssProvDto = new HssProvDtoNew(hssProvDb.getId(), hssProvDb.getImsi(), hssProvDb.getMsisdn(), hssProvDb.getAmbr(), hssProvDb.getNssai(), hssProvDb.getArfb(), hssProvDb.getSar(), hssProvDb.getRat(), hssProvDb.getCn(), hssProvDb.getSmfSel(), hssProvDb.getSmDat(), hssProvDb.getEpsFlag(), hssProvDb.getEpsOdb(), hssProvDb.getHplmnOdb(), hssProvDb.getArd(), hssProvDb.getEpsTpl(), hssProvDb.getContextId(), hssProvDb.getApnContext(),accessLogs.getId());
//			socketClient.connect();
//			String msg = socketClient.sendCommand(setSocketMsgBody(hssProvDb));
//			System.out.println("socket output:" + msg);
//			socketClient.logout();
            return new ResponseEntity<>(hssProvDto, HttpStatus.OK);
        }
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI or MSISDN already exist"));
    }

	

	@Override
	public ResponseEntity getHssProv(String imsi, String msisdn) {
		Optional<HssProvNew> hssProvNew = hssProvRepositoryNew.findByImsiOrMsisdn(imsi, msisdn);
		if (hssProvNew.isPresent()) {
			HssProvNew hssProvDb = hssProvNew.get();
//            socketClient.connect();
//            String msg = setSocketMsgBody(hssProvDb);
//            socketClient.sendCommand(msg);
//            socketClient.logout();
			HssProvDtoNew hssProvDto=new HssProvDtoNew();
			hssProvDto.setHssProvId(hssProvDb.getId());
			hssProvDto.setImsi(hssProvDb.getImsi());
			hssProvDto.setMsisdn(hssProvDb.getMsisdn());
			hssProvDto.setAmbr(hssProvDb.getAmbr());
			hssProvDto.setNssai(hssProvDb.getNssai());
			hssProvDto.setArfb(hssProvDb.getArfb());
			hssProvDto.setSar(hssProvDb.getSar());
			hssProvDto.setRat(hssProvDb.getRat());
			hssProvDto.setCn(hssProvDb.getCn());
			hssProvDto.setSmfSel(hssProvDb.getSmfSel());
			hssProvDto.setSmDat(hssProvDb.getSmDat());
			hssProvDto.setEpsFlag(hssProvDb.getEpsFlag());
			hssProvDto.setEpsOdb(hssProvDb.getEpsOdb());
			hssProvDto.setHplmnOdb(hssProvDb.getHplmnOdb());
			hssProvDto.setArd(hssProvDb.getArd());
			hssProvDto.setEpsTpl(hssProvDb.getEpsTpl());
			hssProvDto.setContextId(hssProvDb.getContextId());
			hssProvDto.setApnContext(hssProvDb.getApnContext());
			hssProvDto.setAccessId(hssProvDb.getAccessLogs().getId());
			return new ResponseEntity<>(hssProvDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "IMSI or MSISDN does n't exist"));
	}
	
	@Override
	public List<HssProvDtoNew> getAllHssProvRecord() {
		List<HssProvNew> hssProvDbList = hssProvRepositoryNew.findAll();
		List<HssProvDtoNew> hssProvDtoList = new ArrayList<>();
		for (HssProvNew hssProvDb : hssProvDbList) {
			HssProvDtoNew hssProvDto = new HssProvDtoNew();
			hssProvDto.setHssProvId(hssProvDb.getId());
			hssProvDto.setImsi(hssProvDb.getImsi());
			hssProvDto.setMsisdn(hssProvDb.getMsisdn());
			hssProvDto.setAmbr(hssProvDb.getAmbr());
			hssProvDto.setNssai(hssProvDb.getNssai());
			hssProvDto.setArfb(hssProvDb.getArfb());
			hssProvDto.setSar(hssProvDb.getSar());
			hssProvDto.setRat(hssProvDb.getRat());
			hssProvDto.setCn(hssProvDb.getCn());
			hssProvDto.setSmfSel(hssProvDb.getSmfSel());
			hssProvDto.setSmDat(hssProvDb.getSmDat());
			hssProvDto.setEpsFlag(hssProvDb.getEpsFlag());
			hssProvDto.setEpsOdb(hssProvDb.getEpsOdb());
			hssProvDto.setHplmnOdb(hssProvDb.getHplmnOdb());
			hssProvDto.setArd(hssProvDb.getArd());
			hssProvDto.setEpsTpl(hssProvDb.getEpsTpl());
			hssProvDto.setContextId(hssProvDb.getContextId());
			hssProvDto.setApnContext(hssProvDb.getApnContext());
			hssProvDto.setAccessId(hssProvDb.getAccessLogs().getId());
			hssProvDtoList.add(hssProvDto);
		}
		return hssProvDtoList;
	}
	

    @Transactional
    public ResponseEntity updateHssProv(String imsi, String msisdn, HssProvDtoNew hssProvDto) throws JsonProcessingException {
        Optional<HssProvNew> hssProv = hssProvRepositoryNew.findByImsiOrMsisdn(imsi, msisdn);
        if (hssProv.isPresent()) {
			HssProvNew hssProvDb = hssProv.get();
			hssProvDb.setAmbr(hssProvDto.getAmbr() != null ? hssProvDto.getAmbr() : hssProvDb.getAmbr());
			hssProvDb.setNssai(hssProvDto.getNssai() != null ? hssProvDto.getNssai() : hssProvDb.getNssai());
			hssProvDb.setArfb(hssProvDto.getArfb() != null ? hssProvDto.getArfb() : hssProvDb.getArfb());
			hssProvDb.setSar(hssProvDto.getSar() != null ? hssProvDto.getSar() : hssProvDb.getSar());
			hssProvDb.setRat(hssProvDto.getRat() != null ? hssProvDto.getRat() : hssProvDb.getRat());
			hssProvDb.setCn(hssProvDto.getCn() != null ? hssProvDto.getCn() : hssProvDb.getCn());
			hssProvDb.setSmfSel(hssProvDto.getSmfSel() != null ? hssProvDto.getSmfSel() : hssProvDb.getSmfSel());
			hssProvDb.setSmDat(hssProvDto.getSmDat() != null ? hssProvDto.getSmDat() : hssProvDb.getSmDat());
			hssProvDb.setEpsFlag(hssProvDto.getEpsFlag() != null ? hssProvDto.getEpsFlag() : hssProvDb.getEpsFlag());
			hssProvDb.setEpsOdb(hssProvDto.getEpsOdb() != null ? hssProvDto.getEpsOdb() : hssProvDb.getEpsOdb());
			hssProvDb.setHplmnOdb(hssProvDto.getHplmnOdb() != null ? hssProvDto.getHplmnOdb() : hssProvDb.getHplmnOdb());
			hssProvDb.setArd(hssProvDto.getArd() != null ? hssProvDto.getArd() : hssProvDb.getArd());
			hssProvDb.setEpsTpl(hssProvDto.getEpsTpl() != null ? hssProvDto.getEpsTpl() : hssProvDb.getEpsTpl());
			hssProvDb.setContextId(hssProvDto.getContextId() != null ? hssProvDto.getContextId() : hssProvDb.getContextId());
			hssProvDb.setApnContext(hssProvDto.getApnContext() != null ? hssProvDto.getApnContext() : hssProvDb.getApnContext());
			
			Optional<AccessLogs> accessLogsDb = accessLogsRepository.findById(hssProvDb.getAccessLogs().getId());
            AccessLogs accessLogs = accessLogsDb.get();
            
			HssProvDtoNew hssProvDtoNew = new HssProvDtoNew(hssProvDb.getId(), hssProvDb.getImsi(),
					hssProvDb.getMsisdn(), hssProvDb.getAmbr(), hssProvDb.getNssai(), hssProvDb.getArfb(),
					hssProvDb.getSar(), hssProvDb.getRat(), hssProvDb.getCn(), hssProvDb.getSmfSel(),
					hssProvDb.getSmDat(), hssProvDb.getEpsFlag(), hssProvDb.getEpsOdb(), hssProvDb.getHplmnOdb(),
					hssProvDb.getArd(), hssProvDb.getEpsTpl(), hssProvDb.getContextId(), hssProvDb.getApnContext(),
					accessLogs.getId());
            
			updateAccessRequestPayload(new Date(), hssProvDtoNew, accessLogs);
			hssProvRepositoryNew.save(hssProvDb);
			
			return new ResponseEntity<>(hssProvDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "IMSI or MSISDN Id does n't exist"));
    }

    @Transactional
    public ResponseEntity deleteHssProv(String imsi, String msisdn) {
		Optional<HssProvNew> hssProv = hssProvRepositoryNew.findByImsiOrMsisdn(imsi, msisdn);
		if (hssProv.isPresent()) {
			hssProvRepositoryNew.deleteByImsiOrMsisdn(imsi, msisdn);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Successfully deleted..."));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "IMSI or MSISDN does n't exist"));
    }

    private void saveAccessRequestPayload(HssProvDtoNew hssProvDto, HssProvNew hssProv, AccessLogs accessLogs) throws JsonProcessingException {
        hssProvDto.setHssProvId(hssProv.getId());
        hssProvDto.setAccessId(accessLogs.getId());
        String reqPayload = convertEntityToJson(hssProvDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
    }

	private void updateAccessRequestPayload(Date date,HssProvDtoNew hssProvDto, AccessLogs accessLogs) throws JsonProcessingException {
		String reqPayload = convertEntityToJson(hssProvDto);
		accessLogs.setReqPayload(reqPayload);
		accessLogsRepository.save(accessLogs);
	}

    private static String convertEntityToJson(HssProvDtoNew hssProvDto) throws JsonProcessingException {
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
