package com.revature.test;

import static org.junit.Assert.assertTrue;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.Admin;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class TestUsers {
	
	public static ArrayList<Account> accounts = new ArrayList<Account>();
	
	/*
	 * This is an example of a singleton design pattern. Only one logger will ever exist.
	 * */
	private static final Logger log = LogManager.getLogger(TestUsers.class);
	
	public static void main (String[] args) {
		log.info("The application has started");
		
	}
	
	public static void recur() {
		recur();
	}
	public static User testUser = new User("dan125" , "Daniel" , "lincoln", "dovetail" , "human" );
	public static UserDAO userDAO = DAOUtilities.getUserDAO();
	
	User result;
	
		@BeforeClass
		public static void initiliazeTest() {
			userDAO.removeUser(testUser);
			assertTrue(userDAO.addUser(testUser));
		
		
	}
		
		@Test
		public void checkUserAdd() {
			UserDAO userDAO = DAOUtilities.getUserDAO();
			Admin a = new Admin();
			a.modifyUser();
			result = userDAO.getUserById(testUser.getUserName());
			assertTrue(testUser.getUserName().equals("Daniella123"));
		}
		
		@AfterClass
		public static void closeTest() {
			
			assertTrue(userDAO.removeUser(testUser));
		}
}
