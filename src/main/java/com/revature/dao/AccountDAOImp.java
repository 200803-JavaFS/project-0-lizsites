package com.revature.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class AccountDAOImp implements AccountDAO {
	
	PreparedStatement ps;
	
	public Set<Account> getAllAccounts(){
		Set<Account> accounts = new HashSet<>();
		try {
			Connection conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM accounts";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account account = new Account (rs.getInt(1), rs.getString(2) , rs.getString(4), rs.getDouble(3));
				accounts.add(account);
		}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	public Account getAccountBySerial(int id) {
		Account a = new Account();
		try {
			Connection conn = DAOUtilities.getConnection();
			String sql = "SELECT * from accounts where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				a.setId(rs.getInt("id"));
				a.setAccountName(rs.getString("name"));
				a.setBalance(rs.getDouble("balance"));
				a.setStatus(rs.getString("status"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	
	/*
	 * This is the general use add account that will be used to add a full new account
	 * */
	@Override
	public boolean addUserToAccount(Account account, User u) {
		try {
			Connection conn = DAOUtilities.getConnection();
			String sql = "BEGIN;" +
						"INSERT INTO accounts_association (userid,id) values (?,?);"+
						"COMMIT";
			ps = conn.prepareStatement(sql);
			int index = 1;
			ps.setString(index++, u.getUserName());
			ps.setInt(index++, account.getId());
		if (ps.executeUpdate()!=0) {
			return true;
		}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * This is used to add a literal account with no user accounts table.
	 * */
	@Override
	public Account addAccount(Account account) {
		
		try {
			Connection conn = DAOUtilities.getConnection();
			String sql1 = "INSERT INTO accounts (name,balance,status) values (?,?,?)";
			ps = conn.prepareStatement(sql1);
			
			ps.setString(1, account.getAccountName());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, "pending");
			ps.executeUpdate();
			
			String sql2 = "SELECT LASTVAL();";
			Statement stt = conn.createStatement();
			ResultSet rs = stt.executeQuery(sql2);
			if (rs.next()) {
				int key = rs.getInt(1);
				System.out.println("key generated: " + key);
				account.setId(key);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return account;
		
	}
	
	@Override
	public boolean addUserToAccount(User u, Account account) {
		try {
		Connection conn = DAOUtilities.getConnection();
		String sql1 = "INSERT INTO accounts_association (userid,id) values (?,?)";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, u.getUserName());
		ps.setInt(2, account.getId());
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
	public boolean removeAccount(Account account) {
		try {
		Connection conn = DAOUtilities.getConnection();
		String sql1 = "DELETE FROM accounts where id=?";
		ps = conn.prepareStatement(sql1);
		ps.setInt(1, account.getId());
		if (ps.executeUpdate()!=0) {
			return true;
		}
		
		System.out.println("removeAccount() has finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean balanceChange(Account account, double amount) {
		if ((account.getBalance() + amount) >= 0 ) {
			account.setBalance(account.getBalance() + amount) ;
			try {
				Connection conn = DAOUtilities.getConnection();
				String sql = "UPDATE accounts SET balance=? where id=?";
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, account.getBalance());
				ps.setInt(2,account.getId());
				if (ps.executeUpdate()!=0) {
					return true;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean transfer(Account targetAccount, Account transferAccount, double amount) {
			if ((targetAccount.getBalance()+amount >= 0 ) && (transferAccount.getBalance() - amount >=0)) {
				targetAccount.setBalance(targetAccount.getBalance() + amount);
				transferAccount.setBalance(transferAccount.getBalance() - amount);
				
				try {
					Connection conn = DAOUtilities.getConnection();
					String sql = "BEGIN;" +
								"UPDATE accounts set balance=? where id=?;" +
								"UPDATE accounts set balance=? where id=?;" +
								"COMMIT;";
					ps = conn.prepareStatement(sql);
					ps.setDouble(1, targetAccount.getBalance());
					ps.setInt(2, targetAccount.getId());
					ps.setDouble(3, transferAccount.getBalance());
					ps.setInt(4, transferAccount.getId());
					if (ps.executeUpdate()!=0) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return false;
	}

	/*------------------------------------------------------------------------------------*/
	
	@Override
	public Set<Account> searchAccountsByUserId(String userid) {
		Set<Account> searchAccount = new HashSet<Account>();
		try {
		Connection conn = DAOUtilities.getConnection();
		String sql1 = "SELECT * FROM accounts INNER JOIN accounts_association ON accounts.id = accounts_association.id where (accounts_association.userid=?)";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, userid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Account account = new Account (rs.getInt(1), rs.getString(2)  ,rs.getString(4),rs.getDouble(3));
			searchAccount.add(account);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return searchAccount;
	}

	@Override
	public boolean updateAccountStatus(Account account) {
		try {
			Connection conn = DAOUtilities.getConnection();
			String sql = "UPDATE accounts set name=?,balance=?,status=? where id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, account.getAccountName());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, account.getStatus());
			ps.setInt(4,  account.getId());
			if (ps.executeUpdate()!=0) {
				return true;
				}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	

	}
	

	
	


