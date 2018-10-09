package com.mojtest.web.api;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojtest.exception.AccountException;
import com.mojtest.model.Account;
import com.mojtest.service.AccountsService;

@RestController
@RequestMapping("/account-project/rest")
public class AccountsController {

	@Autowired
	private AccountsService accountsService;

	@GetMapping(path = "/account/json")
	public ResponseEntity<Collection<Account>> getAccounts() {
		return new ResponseEntity<>(accountsService.getAllAccounts(), HttpStatus.OK);
	}

	@GetMapping(path = "/account/json/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(accountsService.getAccountById(id), HttpStatus.OK);
	}

	@DeleteMapping(path = "/account/json/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Integer id) {
		accountsService.deleteAccount(id);
		return new ResponseEntity<>(new ApiResponse("account successfully deleted"), HttpStatus.OK);
	}

	@PostMapping(path = "/account/json")
	public ResponseEntity<ApiResponse> create(@RequestBody @Valid Account account) {
		accountsService.createAccount(account);
		return new ResponseEntity<>(new ApiResponse("account has been successfully added"), HttpStatus.OK);
	}

	@ExceptionHandler(AccountException.class)
	public ResponseEntity<ApiError> handleException(AccountException accountExcpetion) {
		return new ResponseEntity<>(new ApiError(accountExcpetion.getMessage(), accountExcpetion.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
