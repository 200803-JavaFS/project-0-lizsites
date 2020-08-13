package com.revature.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class AccountDAOImp implements AccountDAO {
	Connection conn;
	PreparedStatement ps;
	
	public Set<Account> getAllAccounts(){
		Set<Account> accounts = new HashSet<>();
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM accounts";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account account = new Account (rs.getString(1), rs.getString(2) , rs.getString(3), rs.getDouble(4));
				accounts.add(account);
		}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	
	/*
	 * This is the general use add account that will be used to add a full new account
	 * */
	@Override
	public boolean addAccount(Account account, User u) {
		if (addAccount(account) && addUserToAccount(u, account)) {
			return true;
		}
		
		return false;
	}
	/*
	 * This is used to add a literal account with no user accounts table.
	 * */
	@Override
	public boolean addAccount(Account account) {
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "INSERT INTO accounts values (?,?,?,?)";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, account.getId());
			ps.setString(2, account.getAccountName());
			ps.setDouble(3, account.getBalance());
			ps.setString(4, account.getStatus());
			if (ps.executeUpdate()!= 0) {
				return true;
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean addUserToAccount(User u, Account account) {
		try {
		conn = DAOUtilities.getConnection();
		String sql1 = "INSERT INTO accounts_association (userid,id) values (?,?)";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, u.getUserName());
		ps.setString(2, account.getId());
		if (ps.executeUpdate()!= 0) {
			return true;
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return false;
	}
	
/*----------------------------------------------------------------------------------*/

	@Override
	public void removeAccount(Account account) {
		try {
		conn = DAOUtilities.getConnection();
		String sql1 = "DELETE FROM accounts where id=?";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, account.getId());
		ps.executeUpdate();
		
		System.out.println("removeAccount() has finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void balanceChange(Account account, double amount) {
		
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM accounts where userid=?";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, account.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				
			
			}
			
			String sql2 = "UPDATE accounts SET accountbalance = ? where (userid=?)";
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/*------------------------------------------------------------------------------------*/
	
	@Override
	public Set<Account> searchAccountsByUserId(String userid) {
		Set<Account> searchAccount = new HashSet<Account>();
		try {
		conn = DAOUtilities.getConnection();
		String sql1 = "SELECT * FROM accounts where id = (select id from accounts_association where userid=?)";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, userid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Account account = new Account (rs.getString(1), rs.getString(2) , rs.getString(3), rs.getDouble(4));
			searchAccount.add(account);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return searchAccount;
	}

	

	}
	

	
	


