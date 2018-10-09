package com.mojtest.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping(path="/account/json/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(accountsService.getAccountById(id),HttpStatus.OK);
	}
}
