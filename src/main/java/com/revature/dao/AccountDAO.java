package com.revature.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.User;

public interface AccountDAO {
	public Set<Account> getAllAccounts();
	public Set<Account> searchAccountsByUserId(String userid);
	
	public Account getAccountBySerial(int id);
	
	
	public Account addAccount(Account account);
	public boolean addUserToAccount(Account account, User u);
	public boolean updateAccountStatus(Account account);
	public boolean removeAccount(Account account);
	public boolean balanceChange(Account account, double amount);
	public boolean transfer (Account account1, Account account2 , double amount);
	public boolean addUserToAccount(User u, Account a);
	
	
	
	}
	

