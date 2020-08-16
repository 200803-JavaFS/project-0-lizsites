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
import com.revature.services.BankManagement;
import com.revature.utilities.DAOUtilities;



public class TestBank {
	
	private static final Logger log = LogManager.getLogger(TestBank.class);
	public static User testUser = new User("Daniella123" , "Daniel" , "lincoln", "dovetail" , "human" );
	public static Account testAccount = new Account("checking" , 5000);
	public static AccountDAO accountDao = DAOUtilities.getAccountDAO();
	public static UserDAO userDAO = DAOUtilities.getUserDAO();
	
	
	@BeforeClass
	public static void init() {
		
		userDAO.removeUser(testUser);
		accountDao.removeAccount(testAccount);
		testAccount = accountDao.addAccount(testAccount);
		userDAO.addUser(testUser);
		assertTrue(accountDao.addUserToAccount(testUser, testAccount));
	}
	
	@Test
	public void testDeposit() {
		BankManagement.runBank();
		testAccount = accountDao.getAccountBySerial(testAccount.getId());
		double result = testAccount.getBalance();
		assertTrue(result == 5100);
	}
	
	@AfterClass
	public static void breakDown() {
//		userDAO.removeUser(testUser);
//		accountDao.removeAccount(testAccount);
	}
	
}
