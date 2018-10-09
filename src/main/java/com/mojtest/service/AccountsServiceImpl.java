package com.mojtest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mojtest.model.Account;
import com.mojtest.repository.AccountRepository;

@Service
public class AccountsServiceImpl implements AccountsService {
	
	@Autowired
	private AccountRepository accountRepository;

	public Collection<Account> getAllAccounts() {
		Collection<Account> allAccounts = accountRepository.findAll();
		return allAccounts;
	}

}
