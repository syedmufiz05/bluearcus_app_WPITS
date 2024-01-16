package com.bluearcus.service;

import com.bluearcus.dto.RatesDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.*;
import com.bluearcus.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RatesServiceImpl implements RatesService {
    @Autowired
    private RatesRepository ratesRepository;
    @Autowired
    private RatingPlanRepository ratingPlanRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private DestinationRatesRepository destinationRatesRepository;
    @Autowired
    private AccessLogsRepository accessLogsRepository;

    @Override
    public RatesDto addRates(RatesDto ratesDto, String authToken) throws JsonProcessingException {
        Optional<Rates> rates = ratesRepository.findById(ratesDto.getRatesId() != null ? ratesDto.getRatesId() : 0);
        if (rates.isPresent()) {
            Rates ratesDb = rates.get();
            ratesDb.setDestName(ratesDto.getDestName() != null ? ratesDto.getDestName() : "");
            ratesDb.setDestType(ratesDto.getDestType() != null ? ratesDto.getDestType() : "");
            ratesDb.setRatesIndex(ratesDto.getIndex() != null ? ratesDto.getIndex() : Integer.valueOf(""));
            ratesDb.setDescription(ratesDto.getDescription() != null ? ratesDto.getDescription() : "");
            ratesDb.setIsRatesActive(ratesDto.getIsRatesActive() != null ? ratesDto.getIsRatesActive() : false);
            Optional<AccessLogs> accessLogsDb = accessLogsRepository.findById(ratesDb.getAccessLogs().getId());
            if (accessLogsDb.isPresent()) {
                AccessLogs accessLogs = accessLogsDb.get();
                ratesRepository.save(ratesDb);
                return saveRatesRequestPayload(ratesDto, ratesDb, accessLogs);
            }
            AccessLogs accessLogs = new AccessLogs();
            accessLogs.setUserId(1212);
            accessLogs.setResponsePayload("");
            accessLogs.setAuthToken(authToken);
            accessLogsRepository.save(accessLogs);
            return saveRatesRequestPayload(ratesDto, ratesDb, accessLogs);
        }

        Rates ratesNew = new Rates();
        ratesNew.setDestName(ratesDto.getDestName() != null ? ratesDto.getDestName() : "");
        ratesNew.setDestType(ratesDto.getDestType() != null ? ratesDto.getDestType() : "");
        ratesNew.setRatesIndex(ratesDto.getIndex() != null ? ratesDto.getIndex() : Integer.valueOf(""));
        ratesNew.setDescription(ratesDto.getDescription() != null ? ratesDto.getDescription() : "");
        ratesNew.setIsRatesActive(ratesDto.getIsRatesActive() != null ? ratesDto.getIsRatesActive() : false);

        AccessLogs accessLogs = new AccessLogs();
        accessLogs.setUserId(1212);
        accessLogs.setResponsePayload("");
        accessLogs.setAuthToken(authToken);
        accessLogsRepository.save(accessLogs);
        ratesNew.setAccessLogs(accessLogs);
        ratesRepository.save(ratesNew);
        return saveRatesRequestPayload(ratesDto, ratesNew, accessLogs);
    }

    @Override
    public ResponseEntity getRates(Integer ratesId) {
        Optional<Rates> rates = ratesRepository.findById(ratesId);
        if (rates.isPresent()) {
            Rates ratesDb = rates.get();
            RatesDto ratesDto = new RatesDto();
            ratesDto.setRatesId(ratesDb.getId());
            ratesDto.setDestName(ratesDb.getDestName());
            ratesDto.setDestType(ratesDb.getDestType());
            ratesDto.setIndex(ratesDb.getRatesIndex());
            ratesDto.setDescription(ratesDb.getDescription());
            ratesDto.setIsRatesActive(ratesDb.getIsRatesActive());
            ratesDto.setAccessId(ratesDb.getAccessLogs().getId());
            return new ResponseEntity<>(ratesDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rates Id does n't exist"));
    }

    @Override
    public List<RatesDto> getAllRates() {
        return ratesRepository.fetchAllRates();
    }

    @Override
    public ResponseEntity editRates(Integer ratesId, RatesDto ratesDto) throws JsonProcessingException {
        Optional<Rates> rates = ratesRepository.findById(ratesId);
        if (rates.isPresent()) {
            Rates ratesDb = rates.get();
            ratesDb.setDestName(ratesDto.getDestName() != null ? ratesDto.getDestName() : ratesDb.getDestName());
            ratesDb.setDestType(ratesDto.getDestType() != null ? ratesDto.getDestType() : ratesDb.getDestType());
            ratesDb.setRatesIndex(ratesDto.getIndex() != null ? ratesDto.getIndex() : ratesDb.getRatesIndex());
            ratesDb.setDescription(ratesDto.getDescription() != null ? ratesDto.getDescription() : ratesDb.getDescription());
            ratesDb.setIsRatesActive(ratesDto.getIsRatesActive() != null ? ratesDto.getIsRatesActive() : ratesDb.getIsRatesActive());

            Optional<AccessLogs> accessLogs = accessLogsRepository.findById(ratesDb.getAccessLogs().getId());
            AccessLogs accessLogsDb = accessLogs.get();
            accessLogsDb.setAccessDateTime(new Date());
            return editRatesRequestPayload(ratesDto, ratesDb, accessLogsDb);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rates Id does n't exist"));
    }

    @Override
    public String deleteRates(Integer ratesId) {
        ratesRepository.deleteById(ratesId);
        return "Rates Details deleted successfully...";
    }


    private RatesDto saveRatesRequestPayload(RatesDto ratesDto, Rates rates, AccessLogs accessLogs) throws JsonProcessingException {
        ratesDto.setRatesId(rates.getId());
        ratesDto.setDestName(rates.getDestName());
        ratesDto.setDestType(rates.getDestType());
        ratesDto.setIndex(rates.getRatesIndex());
        ratesDto.setDescription(rates.getDescription());
        ratesDto.setIsRatesActive(rates.getIsRatesActive());
        ratesDto.setAccessId(accessLogs.getId());
        String reqPayload = convertEntityToJson(ratesDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
        return new RatesDto(ratesDto.getRatesId(), ratesDto.getDestName(), ratesDto.getDestType(), ratesDto.getIndex(), ratesDto.getDescription(), ratesDto.getIsRatesActive(), ratesDto.getAccessId());
    }

    private ResponseEntity editRatesRequestPayload(RatesDto ratesDto, Rates rates, AccessLogs accessLogs) throws JsonProcessingException {
        ratesDto.setRatesId(rates.getId());
        ratesDto.setDestName(rates.getDestName());
        ratesDto.setDestType(rates.getDestType());
        ratesDto.setIndex(rates.getRatesIndex());
        ratesDto.setDescription(rates.getDescription());
        ratesDto.setIsRatesActive(rates.getIsRatesActive());
        ratesDto.setAccessId(accessLogs.getId());
        String reqPayload = convertEntityToJson(ratesDto);
        accessLogs.setReqPayload(reqPayload);
        accessLogsRepository.save(accessLogs);
        RatesDto ratesDtoNew = new RatesDto(ratesDto.getRatesId(), ratesDto.getDestName(), ratesDto.getDestType(), ratesDto.getIndex(), ratesDto.getDescription(), ratesDto.getIsRatesActive(), ratesDto.getAccessId());
        return new ResponseEntity<>(ratesDtoNew, HttpStatus.OK);
    }

    private String convertEntityToJson(RatesDto ratesDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(ratesDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }
}
