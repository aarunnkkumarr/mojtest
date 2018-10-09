package com.mojtest.service;

import java.util.Collection;
import java.util.Optional;

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

	@Override
	public Account getAccountById(Integer id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent()) {
			return account.get();
		}
		throw new RuntimeException("Account doesn't Exist");
	}

	@Override
	public void deleteAccount(Integer id) {
		accountRepository.deleteById(id);
	}
}
