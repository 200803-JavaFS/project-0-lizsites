package com.revature.models;

import java.util.Scanner;
import java.util.Set;

import com.revature.dao.UserDAO;
import com.revature.utilities.DAOUtilities;

public class Admin extends Employee {
	private String role = "admin";
	
	public void modifyUser(){
		
		System.out.println("Please enter User Id of User you would like to modify?");
		Scanner scan = new Scanner(System.in);
		String id = scan.nextLine();
		UserDAO userDao = DAOUtilities.getUserDAO();
		User u = userDao.getUserById(id);
		u.setAccounts(this.getAccountsForUser(u));
		modifyUser(u , userDao);
		}
	
	
	
	public void modifyUser(User u,UserDAO userDao) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println(u);
		System.out.println("What would you like to alter?");
		System.out.println("1. User Id");
		System.out.println("2. Name");
		System.out.println("3. Password");
		System.out.println("4. Role");
		System.out.println("5. Go to Accounts");
		System.out.println("6. exit");
		System.out.println("PLEASE NOTE changes will not persist in the database until 'exit' is chosen");
		String option = scan.nextLine();
		switch (option) {
		
		
			case "1" :
				System.out.println("New User Id:");
				String oldId = u.getUserName();
				String userName = scan.nextLine();
				u.setUserName(userName);
				System.out.println(userName);
				userDao.updateCredential(u, oldId);
				modifyUser(u, userDao);
				break;
				
				
			case "2":
				System.out.println("First Name:");
				String firstName = scan.nextLine();
				if (firstName != null || (firstName.contains(" "))) {
					u.setFirstName(firstName);
				}
				System.out.println("Last Name:");
				String lastName = scan.nextLine();
				if (lastName != null) {
					u.setLastName(lastName);
				}
				modifyUser(u, userDao);
				break;
				
				
			case "3" :
				System.out.println("New Password:");
				u.setPassword(scan.nextLine());
				modifyUser(u, userDao);
				break;
				
				
			case "4" :
				System.out.println("New Role:");
				u.setRole(scan.nextLine());
				modifyUser(u, userDao);
				break;
				
				
			case "5" : 
				Set<Account> accounts = this.getAccountsForUser(u);
				this.updateAccounts(accounts);
				break;
				
			
			case "6" : 
				userDao.updateCredential(u , u.getUserName());
				break;
				
				
			default :
				modifyUser(u, userDao);
				
		}
		
	}
	
	public void updateAccounts (Set<Account> accounts) {
		for (Account a : accounts) {
		System.out.println(a);
		}
		
	}
}
