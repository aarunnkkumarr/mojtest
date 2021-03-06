package com.mojtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mojtest.model.Account;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
	@Autowired
	public AccountRepository accountRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testAccountCreation() {
		// given
		Account account = createTestAccount("John", "Doe", "12345");
		// when
		Account accountCreated = accountRepository.save(account);
		// Assert
		assertThat(accountCreated.getFirstName()).isEqualTo(account.getFirstName());
	}

	@Test(expected = ConstraintViolationException.class)
	public void should_Not_Create_An_AccountThat_AlreadyExist() {
		// given
		Account account = createTestAccount("", "Doe", "12345");
		// when
		accountRepository.save(account);
		entityManager.flush();
		accountRepository.save(account);
		entityManager.flush();
	}

	@Test(expected = ConstraintViolationException.class)
	public void should_Not_Create_An_AccountThat_Has_No_FirstName() {
		// given
		Account account = createTestAccount("", "Doe", "12345");
		// when
		accountRepository.save(account);
	}

	@Test
	public void should_ReturnValidAccount_ById() {
		// given
		Account account = createTestAccount("John", "Doe", "12345");
		// when
		Account found = accountRepository.findById(account.getId()).orElse(null);
		// assert
		assertThat(found.getFirstName()).isEqualTo(account.getFirstName());
	}

	@Test
	public void should_DeletedAccount_ById() {
		// given
		@SuppressWarnings("unused")
		Account account_1 = createTestAccount("John", "Doe", "12345");
		Account account_2 = createTestAccount("John1", "Doe1", "12346");
		// when
		accountRepository.deleteById(account_2.getId());
		assertThat(accountRepository.findAll().size()).isEqualTo(1);
	}

	@Test
	public void should_ReturnAllAccounts() {
		// given
		Account account_1 = createTestAccount("John","Doe","12345");
		Account account_2 = createTestAccount("John1","Doe1","12346");
		Account account_3 = createTestAccount("Alex","Couper","12347");
		// when
		List<Account> accounts = accountRepository.findAll();
		//assert
		assertThat(accounts.size()).isEqualTo(3);
		
		List<String> accountNumbers =accounts.stream().map(a -> a.getAccountNumber()).collect(Collectors.toList());
		
		assertThat(accountNumbers.contains(account_1.getAccountNumber()));
		assertThat(accountNumbers.contains(account_2.getAccountNumber()));
		assertThat(accountNumbers.contains(account_3.getAccountNumber()));
	}
	private Account createTestAccount(String firstName, String secondName, String accountNumber) {
		Account account = new Account();
		account.setFirstName(firstName);
		account.setSecondName(secondName);
		account.setAccountNumber(accountNumber);
		account = entityManager.persist(account);
		entityManager.flush();
		return account;
	}

}
