package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class UserDAOImp implements UserDAO {
	Connection conn;
	PreparedStatement ps;
	
	@Override
	public void addUser(User u) {
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "INSERT INTO users VALUES (? , ? , ? , ?)";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getName());
			ps.setDouble(3, u.getBalance());
			ps.setString(4, u.getPassword());
			ps.executeUpdate(); 
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	@Override
	public void updateUsername (User u) {
		
	}
	
	@Override
	public void balanceChange(User u) {
		
	}
	
	@Override
	public User checkPassword(String password) {
		User u = new User();
		try {
			conn = DAOUtilities.getConnection();
			String sql1 = "SELECT * FROM users where (password=?)";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, password);
			ResultSet rs = ps.executeQuery();
			
			
			boolean isSuccess = (rs != null);
			while (rs.next()) {
				u.setUserName(rs.getString(1));
				u.setName(rs.getString(2));
				u.setBalance(rs.getDouble(3));
				u.setPassword(rs.getString(4));
			}
			
			
			 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return u;
		
	}
}
