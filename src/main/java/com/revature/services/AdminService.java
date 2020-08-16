package com.revature.services;

import java.util.Scanner;
import java.util.Set;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class AdminService extends EmployeeService {
	private String role = "admin";
	
	public void modifyUser(){
		System.out.println("WELCOME ADMIN TO THE BANK OF MONEY STUFF");
		System.out.println("-------------ACTIVE USERS---------------");
		for (User u : viewAllUsers()) {
			System.out.println(u);
		}
		System.out.println("|-----------------------------------------|");
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
		System.out.println("6. exit back to User Selection");
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
				if (firstName != null || !(firstName.contains(" "))) {
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
				modifyUser();
				break;
				
				
			default :
				modifyUser(u, userDao);
				
		}
		
	}
	
	public void updateAccounts (Set<Account> accounts) {
		Scanner scan = new Scanner (System.in);
		for (Account a : accounts) {
		System.out.println(a);
		}
		
		System.out.println("|------------ACCOUNT UPDATE MENU---------------|");
		
		
		System.out.println("Would you like to approve/close any accounts?");
		System.out.println("enter \"y\" for \"yes\" or \"n\" for \"no\"...");
		String answer = scan.nextLine();
		if  (answer.toLowerCase().equals("y")) {
			System.out.println("Enter the id of account you would like to approve/close:");
			int id = scan.nextInt();
			scan.nextLine();
			for (Account a : accounts) {
				if (a.getId()== id) {
					System.out.println("Account to be updated: " + a.getAccountName() +", " +"$ " +a.getBalance()+ ", " + a.getStatus());
					System.out.println("press [1] for approve");
					System.out.println("press [2] for decline");
					System.out.println("press another key to return to the previous menu");
					int choice = scan.nextInt();
					scan.nextLine();
					if (choice==1) {
					AccountDAO accountDAO = DAOUtilities.getAccountDAO();
					a.setStatus("approved");
					accountDAO.updateAccountStatus(a);
					}else if (choice==2) {
					AccountDAO accountDAO = DAOUtilities.getAccountDAO();
					a.setStatus("declined");
					accountDAO.updateAccountStatus(a);
					} else {
						updateAccounts(accounts);
					}
				}
			}
			
		}
		
	}
}
