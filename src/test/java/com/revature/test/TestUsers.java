package com.revature.test;

import static org.junit.Assert.assertTrue;  

import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AdminService;
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
	
	
	
	public static User testUser = new User("Daniella123" , "Daniel" , "lincoln", "dovetail" , "human" );
	public static Account testAccount = new Account("checking" , 5000);
	public static AccountDAO accountDao = DAOUtilities.getAccountDAO();
	public static UserDAO userDAO = DAOUtilities.getUserDAO();
	
	
	
		
	
		@BeforeClass
		public static void initiliazeTest() {
			userDAO.removeUser(testUser);
			assertTrue(userDAO.addUser(testUser));
			
		
	}
		@Test
		public void testAddAccount() {
			accountDao.addAccount(testAccount);
			accountDao.addUserToAccount(testAccount, testUser);
			assertTrue(accountDao.searchAccountsByUserId(testUser.getUserName())!=null);
		}
		
		@Test
		public void checkAdminAlter() {
			
			AdminService a = new AdminService();
			a.modifyUser();
			User result = userDAO.getUserById("Daniella123");
			assertTrue(result.getFirstName().equals("Daniella"));
		}
		
		@Test
		public void roleChange() {
			
			AdminService a = new AdminService();
			a.modifyUser();
			User result = userDAO.getUserById("Daniella123");
			assertTrue(result.getRole().equals("Employee"));
		}
		
		@Test
		public void passwordChange() {
			
			AdminService a = new AdminService();
			a.modifyUser();
			User result = userDAO.getUserById("Daniella123");
			assertTrue(result.getPassword().equals("password1"));
		}
		
		@Test
		public void seeAccounts() {
			AdminService a = new AdminService();
			a.modifyUser();
			Set<Account> resultTest = accountDao.searchAccountsByUserId(testUser.getUserName());
			assertTrue(resultTest!=null);
		}
		
		@AfterClass
		public static void closeTest() {
			
			assertTrue(userDAO.removeUser(testUser));
		}
}
