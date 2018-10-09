package com.mojtest.web.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojtest.exception.AccountException;
import com.mojtest.model.Account;
import com.mojtest.service.AccountsService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsController.class)
public class AccountsControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountsService accountsService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void should_ReturnAllValidAccounts_Json() throws Exception {
		// ARRANGE
		Account account_1 = createTestAccount(1, "John", "Doe", "12345");
		Account account_2 = createTestAccount(2, "Simon", "Doe", "12346");
		Account account_3 = createTestAccount(3, "Paul", "Doe", "12347");
		List<Account> accounts = Arrays.asList(account_1, account_2, account_3);
		when(accountsService.getAllAccounts()).thenReturn(accounts);

		// ACT AND ASSSERT
		mvc.perform(get("/account-project/rest/account/json").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].firstName", is(account_1.getFirstName())));

	}

	@Test
	public void should_Return_CorrectAccount_On_RetrivalById() throws Exception {
		// ARRANGE
		Account account_1 = createTestAccount(1, "John", "Doe", "12345");

		when(accountsService.getAccountById(1)).thenReturn(account_1);

		// ACT AND ASSSERT
		mvc.perform(get("/account-project/rest/account/json/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is(account_1.getFirstName())));
	}

	@Test
	public void should_Return_Error_When_Account_Does_Not_Exist() throws Exception {
		// ARRANGE
		when(accountsService.getAccountById(12)).thenThrow(new AccountException("Account number Already Exist"));
		// ACT AND ASSSERT
		mvc.perform(get("/account-project/rest/account/json/12").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Account number Already Exist")));

	}

	@Test
	public void should_Delete_CorrectAccount_Successfully() throws Exception {
		// ARRANGE
		doThrow(IllegalArgumentException.class).when(accountsService).deleteAccount(null);
		// ACT AND ASSSERT
		mvc.perform(delete("/account-project/rest/account/json/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("account successfully deleted"));

	}

	@Test
	public void should_CreateNewAccount_Return_Success_Message() throws Exception {

		Account account_1 = createTestAccount(1, "John", "Woo", "12346");
		String json = mapper.writeValueAsString(account_1);

		when(accountsService.createAccount(account_1)).thenReturn(account_1);
		mvc.perform(post("/account-project/rest/account/json").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("account has been successfully added"));

	}

	private Account createTestAccount(int id, String firstName, String secondName, String accountNumber) {
		Account account = new Account();
		account.setId(id);
		account.setFirstName(firstName);
		account.setSecondName(secondName);
		account.setAccountNumber(accountNumber);
		return account;
	}

}
