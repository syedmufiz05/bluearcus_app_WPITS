package com.bluearcus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.dto.PostpaidAccountsDtoNew;
import com.bluearcus.service.PostpaidAccountsService;

@RestController
@RequestMapping("/api/postpaid/account")
@CrossOrigin("http://172.5.10.2:8090/")
public class PostpaidAccountsController {

	@Autowired
	private PostpaidAccountsService postpaidAccountsService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<PostpaidAccountsDto> savePostPaidAccount(@RequestBody PostpaidAccountsDto postpaidAccountsDto) {
		return postpaidAccountsService.savePostpaidAccount(postpaidAccountsDto);
	}

	@RequestMapping(value = "/get/{account_id}", method = RequestMethod.GET)
	public ResponseEntity<PostpaidAccountsDtoNew> getPostPaidAccount(@PathVariable("account_id") Integer accountId) {
		return postpaidAccountsService.getPostpaidAccount(accountId);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<PostpaidAccountsDtoNew> getAllPostPaidAccounts() {
		return postpaidAccountsService.getAllPostpaidAccounts();
	}
}

