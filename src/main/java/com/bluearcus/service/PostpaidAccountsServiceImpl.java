package com.bluearcus.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.dto.PostpaidAccountsDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PostpaidAccounts;
import com.bluearcus.repo.PostpaidAccountsRepo;

@Service
public class PostpaidAccountsServiceImpl implements PostpaidAccountsService {

	@Autowired
	private PostpaidAccountsRepo postpaidAccountsRepo;
	
	@Autowired
	private FlatFileService flatFileService;

	@Override
	public ResponseEntity savePostpaidAccount(PostpaidAccountsDto postpaidAccountDto) {
		Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findById(postpaidAccountDto.getAccountId() != null ? postpaidAccountDto.getAccountId() : 0);
		if (!postpaidAccount.isPresent()) {
			PostpaidAccounts postpaidAccountDb = new PostpaidAccounts();
			postpaidAccountDb.setCustomerId(postpaidAccountDto.getCustomerId() != null ? postpaidAccountDto.getCustomerId() : Integer.valueOf(""));
			postpaidAccountDb.setCallingNumber(postpaidAccountDto.getCallingNumber() != null ? postpaidAccountDto.getCallingNumber() : "");
			postpaidAccountDb.setCalledNumber(postpaidAccountDto.getCalledNumber() != null ? postpaidAccountDto.getCalledNumber() : "");
			postpaidAccountDb.setCallDuration(postpaidAccountDto.getCallDuration() != null ? postpaidAccountDto.getCallDuration() : Integer.valueOf(""));
			postpaidAccountDb.setCallType(postpaidAccountDto.getCallType() != null ? postpaidAccountDto.getCallType() : "");
			postpaidAccountDb.setDataOctetsSessionConsumed(postpaidAccountDto.getDataOctetsSessionConsumed() != null ? postpaidAccountDto.getDataOctetsSessionConsumed() : Integer.valueOf(""));
			postpaidAccountDb.setSmsDestinationNumber(postpaidAccountDto.getSmsDestinationNumber() != null ? postpaidAccountDto.getSmsDestinationNumber() : "");
			postpaidAccountDb.setSmsConsumedCount(postpaidAccountDto.getSmsConsumedCount() != null ? postpaidAccountDto.getSmsConsumedCount() : Integer.valueOf(""));
			postpaidAccountsRepo.save(postpaidAccountDb);
			PostpaidAccountsDtoNew postpaidAccountsDtoNew = new PostpaidAccountsDtoNew(postpaidAccountDb.getAccountId(),
					postpaidAccountDb.getCustomerId(), postpaidAccountDb.getCallingNumber(),
					postpaidAccountDb.getCalledNumber(), fetchReadableDateTime(postpaidAccountDb.getCallStart()),
					fetchReadableDateTime(postpaidAccountDb.getCallEnd()), postpaidAccountDb.getCallDuration(),
					postpaidAccountDb.getCallType(),
					fetchReadableDateTime(postpaidAccountDb.getDataOctetsSessionStart()),
					fetchReadableDateTime(postpaidAccountDb.getDataOctetsSessionEnd()),
					postpaidAccountDb.getDataOctetsSessionConsumed(), postpaidAccountDb.getSmsDestinationNumber(),
					postpaidAccountDb.getSmsConsumedCount(),
					fetchReadableDateTime(postpaidAccountDb.getSmsConsumedDate()));
			
			// Storing data for flat file
			String dataForFlatFile = postpaidAccountsDtoNew.toString();
			flatFileService.storeUserData("call", postpaidAccountsDtoNew.getCallStart(), postpaidAccountsDtoNew.getCustomerId(), dataForFlatFile);
			
			return new ResponseEntity<>(postpaidAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "Account Id already exist"));
	}

	@Override
	public ResponseEntity getPostpaidAccount(Integer accountId) {
		Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findById(accountId);
		if (postpaidAccount.isPresent()) {
			PostpaidAccounts postpaidAccountDb = postpaidAccount.get();
			PostpaidAccountsDtoNew postpaidAccountsDto = new PostpaidAccountsDtoNew();
			postpaidAccountsDto.setAccountId(postpaidAccountDb.getAccountId());
			postpaidAccountsDto.setCustomerId(postpaidAccountDb.getCustomerId());
			postpaidAccountsDto.setCallingNumber(postpaidAccountDb.getCallingNumber());
			postpaidAccountsDto.setCalledNumber(postpaidAccountDb.getCalledNumber());
			postpaidAccountsDto.setCallStart(fetchReadableDateTime(postpaidAccountDb.getCallStart()));
			postpaidAccountsDto.setCallEnd(fetchReadableDateTime(postpaidAccountDb.getCallEnd()));
			postpaidAccountsDto.setCallDuration(postpaidAccountDb.getCallDuration());
			postpaidAccountsDto.setCallType(postpaidAccountDb.getCallType());
			postpaidAccountsDto.setDataOctetsSessionStart(fetchReadableDateTime(postpaidAccountDb.getDataOctetsSessionStart()));
			postpaidAccountsDto.setDataOctetsSessionEnd(fetchReadableDateTime(postpaidAccountDb.getDataOctetsSessionEnd()));
			postpaidAccountsDto.setDataOctetsSessionConsumed(postpaidAccountDb.getDataOctetsSessionConsumed());
			postpaidAccountsDto.setSmsDestinationNumber(postpaidAccountDb.getSmsDestinationNumber());
			postpaidAccountsDto.setSmsConsumedCount(postpaidAccountDb.getSmsConsumedCount());
			postpaidAccountsDto.setSmsConsumedDate(fetchReadableDateTime(postpaidAccountDb.getSmsConsumedDate()));
			return new ResponseEntity<>(postpaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Account Id already exist"));
	}

	@Override
	public List<PostpaidAccountsDtoNew> getAllPostpaidAccounts() {
		List<PostpaidAccountsDto> postpaidAccountsDbList = postpaidAccountsRepo.fetchAllPostpaidAccounts();
		List<PostpaidAccountsDtoNew> postpaidAccountsDtoList = new ArrayList<>();
		
		for (PostpaidAccountsDto postpaidAccountsDto : postpaidAccountsDbList) {
			PostpaidAccountsDtoNew postpaidAccountsDtoNew = new PostpaidAccountsDtoNew();
			postpaidAccountsDtoNew.setAccountId(postpaidAccountsDto.getAccountId());
			postpaidAccountsDtoNew.setCustomerId(postpaidAccountsDto.getCustomerId());
			postpaidAccountsDtoNew.setCallingNumber(postpaidAccountsDto.getCallingNumber());
			postpaidAccountsDtoNew.setCalledNumber(postpaidAccountsDto.getCalledNumber());
			postpaidAccountsDtoNew.setCallStart(fetchReadableDateTime(postpaidAccountsDto.getCallStart()));
			postpaidAccountsDtoNew.setCallEnd(fetchReadableDateTime(postpaidAccountsDto.getCallEnd()));
			postpaidAccountsDtoNew.setCallDuration(postpaidAccountsDto.getCallDuration());
			postpaidAccountsDtoNew.setCallType(postpaidAccountsDto.getCallType());
			postpaidAccountsDtoNew.setDataOctetsSessionStart(fetchReadableDateTime(postpaidAccountsDto.getDataOctetsSessionStart()));
			postpaidAccountsDtoNew.setDataOctetsSessionEnd(fetchReadableDateTime(postpaidAccountsDto.getDataOctetsSessionEnd()));
			postpaidAccountsDtoNew.setDataOctetsSessionConsumed(postpaidAccountsDto.getDataOctetsSessionConsumed());
			postpaidAccountsDtoNew.setSmsDestinationNumber(postpaidAccountsDto.getSmsDestinationNumber());
			postpaidAccountsDtoNew.setSmsConsumedCount(postpaidAccountsDto.getSmsConsumedCount());
			postpaidAccountsDtoNew.setSmsConsumedDate(fetchReadableDateTime(postpaidAccountsDto.getSmsConsumedDate()));
			postpaidAccountsDtoList.add(postpaidAccountsDtoNew);
		}
		return postpaidAccountsDtoList;
	}
	
	private String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
}
