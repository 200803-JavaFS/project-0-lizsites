package com.revature.services;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class EmployeeService {
	
	private String role = "employee";
	
	
	public void modifyUserWelcomeScreen(){
		System.out.println("WELCOME EMPLOYEE TO THE BANK OF MONEY STUFF");
		System.out.println("-------------ACTIVE USERS---------------");
		for (User u : viewAllUsers()) {
			System.out.println(u);
		}
		System.out.println("|-----------------------------------------|");
		AccountDAO accountDAO = DAOUtilities.getAccountDAO();
		Set<Account> all = accountDAO.getAllAccounts();
		Set<Account>  pending = new HashSet<>();
		for (Account a : all) {
			if (a.getStatus().equals("pending")) {
				pending.add(a);
			}
		}
		updateAccounts(pending);
	}
		
	

	public void updateAccounts (Set<Account> accounts) {
		Scanner scan = new Scanner (System.in);
		
		
		System.out.println("|------------ACCOUNT UPDATE MENU---------------|");
		System.out.println("");
		System.out.println("AS AN EMPLOYEE YOU ARE LIMITED TO APPROVAL AND DENIAL OF ACCOUNTS");
		System.out.println("");
		System.out.println("_____________Pending Accounts___________________");
		for (Account a : accounts) {
			System.out.println(a);
			}
			System.out.println("Enter the id of account you would like to approve/close:");
			try {
			int id = scan.nextInt();
			scan.nextLine();
			
			
			Account openAccount = new Account();
			for (Account a : accounts) {
				if ((a.getId()== id)&& a.getStatus().equals("pending")) {
					openAccount = a;
			}
			}
				System.out.println("Account to be updated: " + openAccount.getAccountName() +", " +"$ " +openAccount.getBalance()+ ", " + openAccount.getStatus());
				System.out.println("press [1] for approve");
				System.out.println("press [2] for decline");
				System.out.println("press another key to return to change menus");
				int choice = scan.nextInt();
				scan.nextLine();
				if (choice==1) {
				AccountDAO accountDAO = DAOUtilities.getAccountDAO();
				openAccount.setStatus("approved");
				accountDAO.updateAccountStatus(openAccount);
				updateAccounts(accounts);
				}else if (choice==2) {
				AccountDAO accountDAO = DAOUtilities.getAccountDAO();
				openAccount.setStatus("declined");
				accountDAO.updateAccountStatus(openAccount);
				updateAccounts(accounts);
				} else {
					System.out.println("go to log in screen?");
					System.out.print("[y] / [n] ");
					String answer = scan.nextLine();
					if (answer.toLowerCase().equals("y")) {
						BankManagement.login();
					} else
					updateAccounts(accounts);
				}
			} catch(InputMismatchException e) {
				updateAccounts(accounts);
			}
			
			} 
			
		
		


	public Set<User> viewAllUsers(){ 
		UserDAO userDao = DAOUtilities.getUserDAO();
		return userDao.getAllUsers();
}
	
	public Set<Account> getAccountsForUser(User u){
		AccountDAO accountDAO = DAOUtilities.getAccountDAO();
		return accountDAO.searchAccountsByUserId(u.getUserName());
	}
}
