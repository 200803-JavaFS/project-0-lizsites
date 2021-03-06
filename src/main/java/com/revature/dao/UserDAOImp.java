package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.revature.models.User;
import com.revature.services.BankManagement;
import com.revature.utilities.DAOUtilities;

public class UserDAOImp implements UserDAO {
	private static final org.apache.logging.log4j.Logger userLog = LogManager.getLogger(UserDAOImp.class);
	Connection conn;
	PreparedStatement ps;
	private static AccountDAO accountDAO = DAOUtilities.getAccountDAO();
	
	public Set<User> getAllUsers(){
		Set<User> users = new HashSet<>();
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM users";
			ps = conn.prepareStatement(sql1);
		
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				String userid = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String password = rs.getString(4);
				String role = rs.getString(5);
				
				
				
				User u = new User(userid,firstName, lastName, password, role);
				
				users.add(u);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	@Override
	public boolean addUser(User u) {
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "INSERT INTO users (userid, first_name, last_name, password, roles) VALUES (? , ? , ?, ?, ?)";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getFirstName());
			ps.setString(3, u.getLastName());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getRole());
			if (ps.executeUpdate()!=0) {
				userLog.info("New user " + u.getFirstName() + "has been made");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/*
	 * I'm going to attempt to use generics to 
	 * avoid writing redundant DAO methods.
	 * 08/13/2020 -- It didn't work
	 * */
	
	@Override
	public boolean updateCredential (User u, String oldId) {
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "UPDATE users SET userid=?, first_name=?,last_name=?, password=?,roles=? where userid=?";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getFirstName());
			ps.setString(3, u.getLastName());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getRole());
			ps.setString(6, oldId);
			if (ps.executeUpdate()!=0) {
				userLog.info("User " + u.getFirstName() + "has been updated");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	
	@Override
	public boolean removeUser(User u) {
		try {
		conn = DAOUtilities.getConnection();
		String sql1 = "delete from users where userid=?";
		ps = conn.prepareStatement(sql1);
		ps.setString(1, u.getUserName());
		if (ps.executeUpdate()!=0) {
			return true;
		}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override 
	public User getUserById(String id) {
		User u = new  User();
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM users where userid=?";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String username = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String password = rs.getString(4);
				String roles = rs.getString(5);
				
				u.setUserName(username);
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setPassword(password);
				u.setRole(roles);
				u.setAccounts(accountDAO.searchAccountsByUserId(username));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
}
