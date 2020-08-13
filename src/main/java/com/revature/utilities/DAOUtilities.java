package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImp;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImp;

/*
	 * I'm mostly regurgitating the code from my Pub Hub project just to get 
	 * a head start on this. However, I will not be copying and pasting,
	 * if not because I would feel a little bit dirty about it.
	 * 
	 * This will be the class that setups and confirms a connection between the console,
	 * and PostgreSQL
	 * */

public class DAOUtilities {
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "password";
	private static final String CONNECTION_URL = "jdbc:postgresql://javafs200803.cg0rpxexvjxn.us-east-2.rds.amazonaws.com/bankofliz";
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			
			try {
			Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!!!!!!!!!!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(CONNECTION_URL , CONNECTION_USERNAME , CONNECTION_PASSWORD );
		}
		
		if (connection.isClosed()) {
			System.out.println("Opening a new connection!!!!!!!");
			connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD );
		}
		
		if (connection == null) {
			System.out.println("connection is closed....");
		} else {
			System.out.println("Connection successful!!!!");
		}
		
		return connection;
	}
	
	/*
	 * Conceptually, I don't perfectly understand why we want to store
	 * a implementation of an interface inside an interface reference variable,
	 * BUT it worked :)
	 * */
	public static UserDAO getUserDAO () {
		return new UserDAOImp();
	}
	
	public static AccountDAO getAccountDAO () {
		return new AccountDAOImp();
	}
	
	
	public static void main (String[]args) {
		
		try(Connection conn = DAOUtilities.getConnection()){
			System.out.println("yay you did it!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	
