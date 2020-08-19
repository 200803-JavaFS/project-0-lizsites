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
		userDAO.addUser(testUser);
		assertTrue(accountDao.testFunction(testAccount,testUser));
	}
	
	@Test
	public void testDeposit() {
		BankManagement.runBank();
		testAccount = accountDao.getAccountBySerial(testAccount.getId());
		double result = testAccount.getBalance();
		assertTrue(result == 5100);
	}
	
	@Test
	public void testWithDrawal() {
		BankManagement.runBank();
		testAccount = accountDao.getAccountBySerial(testAccount.getId());
		double result = testAccount.getBalance();
		assert(result == 4900);
	}
	
	@Test
	public void testTransfer() {
		testAccount = accountDao.getAccountBySerial(testAccount.getId());
		Account testAccount2 = new Account("test savings" , 6000);
		accountDao.testFunction(testAccount2, testUser);
		BankManagement.runBank();
		double result = testAccount2.getBalance();
		assertTrue(result==5000);
	}
	
	@AfterClass
	public static void breakDown() {
		userDAO.removeUser(testUser);
		accountDao.removeAccount(testAccount);
	}
	
}
