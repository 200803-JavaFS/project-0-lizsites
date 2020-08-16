package com.revature.services;

import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImp;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class BankManagement {
	
	private static final org.apache.logging.log4j.Logger bankLog = LogManager.getLogger(BankManagement.class);
	private static Scanner scan = new Scanner(System.in);
	private static UserDAO userDao = DAOUtilities.getUserDAO();
	private static AccountDAO accountDao = DAOUtilities.getAccountDAO();
	public static void runBank(){
		
		
		bankLog.info("bank has opened!!");
		
		
		
		System.out.println("./startup.sh ");
		System.out.println("The server starts, and you wait awhile");
		
		
		System.out.println("----- Z3NAFIDE HAS ENTERED THE CHAT -------");
		System.out.println("ARE YOU A NEW USER?");
		System.out.println("Y/N");
		String answer = scan.nextLine();
		if (answer.contains("y")) {
			createUser();
		} else {
			login();
		}
		}
		
		public static void login() {
		System.out.println("Welcome to Deep Vine Internal, credentials human");
		System.out.println("ENTER YOUR USERNAME");
		String username = scan.nextLine();
		
		User u = userDao.getUserById(username);
		u.setAccounts(accountDao.searchAccountsByUserId(u.getUserName()));
		if (u!=null){
		bankLog.info("user has logged in!");
		System.out.println(u);
		System.out.println("ENTER YOUR PASSWORD " + u.getUserName());
		String password = scan.nextLine();
		
		if (password.equals(u.getPassword())) {
			System.out.println("Does the meatbag request services?");
			printUserOptions(u);
		}
	} 
		System.out.println("Failure to login");
		runBank();
	}
	
	
	public static void printUserOptions(User u) {
		int options = 0;
		List<String> optionList = new ArrayList<>();
		System.out.println("Available acounts:");
		for (Account a : u.getAccounts()) {
			System.out.println(a);
		}
		
		String option1 = "Withdraw";
		String option2 = "Deposit";
		String option3 = "Change alias";
		String option4 = "Add companion";
		String option5 = "shock and burn";
		
		optionList.add(option1);
		optionList.add(option2);
		optionList.add(option3);
		optionList.add(option4);
		optionList.add(option5);
		for (String s : optionList) {
			options++;
			System.out.println("[" +options +"]" + ". " + s);
		}
		String option = scan.nextLine();
		switch (option) {
		case  "1" : 
			System.out.println("Select Account by Id");
			int choice = scan.nextInt();
			scan.nextLine();
			for (Account a : u.getAccounts()) {
				if (a.getId()==choice) {
					System.out.println("Amount to be Deposited:");
					double amount = scan.nextDouble();
					scan.nextLine();
					accountDao.balanceChange(a, amount);
				}
			}
			
			
			break;
		case  "2" :
			
			break;
		case  "3" :
			
			break;
		case  "4" :
			
			break;
		case  "5" :
			
			break;
		default :
		}
		
	}
	
	public static void scanChoice(int choice) {
		
	}
	
	/*
	 * The purpose this method is to possibly reduce the amount of trips
	 * the application will take to the database.
	 * 
	 * While the difference may be negligible on the scale we're operating on,
	 * I think it would be good practice for later projects.
	 * */
	
	public static void createUser() {
		
		System.out.println("Enter username:");
		String userId = scan.nextLine();
		User u = new User(userId, null, null, null);
		if (!userDao.addUser(u)) {
			System.out.println("Username has already been taken");
			runBank();
		} else {
			System.out.println("First Name:");
			String firstName = scan.nextLine();
			System.out.println("Last Name");
			String lastName = scan.nextLine();
			System.out.println("Password:");
			String password = scan.nextLine();
			u = new User(u.getUserName(), firstName, lastName, password);
			userDao.updateCredential(u, u.getUserName());
			login();
			
		}
	}
	
	
	
}
