package com.mojtest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

	@Test
	public void should_Return_EmptyList_If_No_AccountsExists() {
		when(accountRepository.findAll()).thenReturn((Collections.emptyList()));
		Collection<Account> actual = accountsService.getAllAccounts();
		assertThat(actual).isEmpty();
	}

	@Test
	public void should_Return_CorrectAccount_WhenAvailable() {
		// ARRANGE
		Account account_1 = createTestAccount(1, "John", "Doe", "123450");
		when(accountRepository.findById(account_1.getId())).thenReturn(Optional.of(account_1));

		// ACT
		Account actual = accountsService.getAccountById(1);

		// ASSERT
		assertThat(actual.getAccountNumber()).isEqualTo(account_1.getAccountNumber());
		assertThat(actual.getFirstName()).isEqualTo(account_1.getFirstName());
		assertThat(actual.getSecondName()).isEqualTo(account_1.getSecondName());
	}

	@Test(expected = RuntimeException.class)
	public void should_ThrowException_WhenAccountDoesNotExist() {
		// ARRANGE
		Account account_1 = createTestAccount(1, "John", "Doe", "123450");
		when(accountRepository.findById(account_1.getId())).thenReturn(Optional.of(account_1));
		// ACT
		accountsService.getAccountById(2);
	}

	@Test
	public void should_DeleteAccount_IfAccountExist() {
		accountsService.deleteAccount(1);
		ArgumentCaptor<Integer> arg = ArgumentCaptor.forClass(Integer.class);
		verify(accountRepository, times(1)).deleteById(arg.capture());
		assertThat(arg.getValue()).isEqualTo(1);
	}

	@Test
	public void should_CreateAccount() {
		Account account_1 = createTestAccount(1, "John", "Doe", "123450");
		when(accountRepository.save(account_1)).thenReturn(account_1);
		Account createdAccount = accountsService.createAccount(account_1);
		assertThat(createdAccount.getAccountNumber()).isEqualTo(account_1.getAccountNumber());
		assertThat(createdAccount.getFirstName()).isEqualTo(account_1.getFirstName());
		assertThat(createdAccount.getSecondName()).isEqualTo(account_1.getSecondName());

	}

}
