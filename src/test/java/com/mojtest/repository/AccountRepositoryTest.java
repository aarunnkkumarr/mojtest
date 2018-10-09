package com.mojtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
