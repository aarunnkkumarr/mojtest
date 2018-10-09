package com.mojtest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.mojtest.model.Account;
import com.mojtest.repository.AccountRepository;

@RunWith(SpringRunner.class)
public class AccountsServiceImplTest {

	@Mock
	private AccountRepository accountRepository;

	private List<Account> accounts;

	@InjectMocks
	private AccountsService accountsService = new AccountsServiceImpl();

	@Before
	public void setup() {
		Account account_1 = createTestAccount(1, "John", "Doe", "123450");
		Account account_2 = createTestAccount(2, "Alex", "Doe", "123451");
		Account account_3 = createTestAccount(3, "Mathew", "Doe", "123452");
		accounts = Arrays.asList(account_1, account_2, account_3);
		when(accountRepository.findAll()).thenReturn((List<Account>) accounts);
	}

	private Account createTestAccount(Integer id, String firstName, String secondName, String accountNumber) {
		Account account = new Account();
		account.setId(id);
		account.setFirstName(firstName);
		account.setSecondName(secondName);
		account.setAccountNumber(accountNumber);
		return account;
	}

	@Test
	public void should_Return_AllAccounts() {
		Collection<Account> actual = accountsService.getAllAccounts();
		assertThat(accounts.size()).isEqualTo(actual.size());
		List<String> accountNumbers = accounts.stream().map(a -> a.getAccountNumber()).collect(Collectors.toList());
		List<String> actualAccountNumbers = actual.stream().map(a -> a.getAccountNumber()).collect(Collectors.toList());
		assertThat(accountNumbers.containsAll(actualAccountNumbers)).isTrue();
	}

}
