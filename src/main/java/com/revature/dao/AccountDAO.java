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
	
	
	public boolean addAccount(Account account);
	public boolean addAccount(Account account, User u);
	public void removeAccount(Account account);
	public void balanceChange(Account account , double amount);
	public boolean addUserToAccount(User u, Account a);
	
	
	
	}
	

