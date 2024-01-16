package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.dto.PostpaidAccountsDtoNew;

public interface PostpaidAccountsService {
	ResponseEntity savePostpaidAccount(PostpaidAccountsDto postpaidAccountsDto);

	ResponseEntity getPostpaidAccount(Integer accountId);

	List<PostpaidAccountsDtoNew> getAllPostpaidAccounts();
}
