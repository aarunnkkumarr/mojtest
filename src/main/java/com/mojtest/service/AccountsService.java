package com.mojtest.service;

import java.util.Collection;

import com.mojtest.model.Account;


public interface AccountsService {
	public Collection<Account> getAllAccounts();
	public Account getAccountById(Integer id);
	public void deleteAccount(Integer id); 
	public Account createAccount(Account account);

}
