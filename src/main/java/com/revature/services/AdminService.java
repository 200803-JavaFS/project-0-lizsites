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
	
	public void modifyUserWelcomeScreen(){
		System.out.println("WELCOME ADMIN TO THE BANK OF MONEY STUFF");
		System.out.println("-------------ACTIVE USERS---------------");
		for (User u : viewAllUsers()) {
			System.out.println(u);
		}
		System.out.println("|-----------------------------------------|");
		System.out.println("Would you like to View and Update Users or Accounts?");
		System.out.println("[1] for Users");
		System.out.println("[2] for Accounts");
		System.out.println("[3] to exit to Customer Console");
		
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		if (choice == 1) {
		modifyUser();
		} else if (choice == 2) {
			AccountDAO accountDAO =DAOUtilities.getAccountDAO();
			Set<Account> accounts = accountDAO.getAllAccounts();
			updateAccounts(accounts, accountDAO);
		} else if (choice == 3) {
			BankManagement.login();
		} else {
			System.out.println("Incorrect Input!!!");
			modifyUserWelcomeScreen();
		}
	}
		
		public void modifyUser() {
		System.out.println("Please enter User Id of user and user's accounts you would like to modify?");
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
		System.out.println("6. exit back to Welcome Screen");
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
				userDao.updateCredential(u, u.getUserName());
				AccountDAO accountDAO = DAOUtilities.getAccountDAO();
				Set<Account> accounts = accountDAO.getAllAccounts();
				this.updateAccounts(accounts, accountDAO);
				break;
				
			
			case "6" : 
				userDao.updateCredential(u , u.getUserName());
				modifyUserWelcomeScreen();
				break;
				
				
			default :
				modifyUser(u, userDao);
				
		}
		
	}
	
	public void updateAccounts (Set<Account> accounts, AccountDAO accountDAO) {
		Scanner scan = new Scanner (System.in);
		
		System.out.println("|------------ACCOUNT UPDATE MENU---------------|");
		
		
		for (Account a : accounts) {
			System.out.println(a);
		}
			System.out.println("|------------------------------------------|");
			System.out.println("Enter the id of account you would like to modify:");
			int id = scan.nextInt();
			scan.nextLine();
			for (Account a : accounts) {
				if (a.getId()== id) {
					System.out.println("Account to be updated: " + a.getAccountName() +", " +"$ " +a.getBalance()+ ", " + a.getStatus());
					System.out.println("press [1] for approve");
					System.out.println("press [2] for decline");
					System.out.println("press [3] to make a deposit");
					System.out.println("press [4] to make a withdrawal");
					System.out.println("press [5] to make a transfer");
					System.out.println("press [6] to go to User Menu");
					System.out.println("press [7] to exit Customer Console");
					int choice = scan.nextInt();
					scan.nextLine();
					
					switch (choice) {
					case 1 :

						a.setStatus("approved");
						accountDAO.updateAccountStatus(a);
						updateAccounts(accounts, accountDAO);
						break;
						
					case 2 :
						a.setStatus("declined");
						accountDAO.updateAccountStatus(a);
						updateAccounts(accounts,accountDAO);
						break;
						
						
					case 3 :
						System.out.println("account= " + a.getAccountName() + ", balance= $ " +a.getBalance() +", status= " + a.getStatus());
						System.out.print("Amount to be Deposited:    ");
						double amount = scan.nextDouble();
						scan.nextLine();
						if (accountDAO.balanceChange(a, amount)) {
							System.out.println("Deposit successful!!!!");
							updateAccounts(accounts,accountDAO);
						} else {
							System.out.println("Overdraw Error!!!!!");
							updateAccounts(accounts,accountDAO);
						}
						break;
					case 4 :
						System.out.println("account= " + a.getAccountName() + ", balance= $ " +a.getBalance() +", status= " + a.getStatus());
						System.out.print("Amount to be Withdrawn: ");
						double withdrawal = scan.nextDouble();
						scan.nextLine();
						accountDAO = DAOUtilities.getAccountDAO();
						if (accountDAO.balanceChange(a, -withdrawal)) {
							System.out.println("Withdrawal successful!!!!");
							updateAccounts(accounts,accountDAO);
						} else {
							System.out.println("Overdraw Error!!!!!!!");
							updateAccounts(accounts,accountDAO);
						}
						break;
						
					case 5 :
						System.out.println("Please enter the id of the account you would like to transfer to: ");
						System.out.print("Id: ");
						id = scan.nextInt();
						scan.nextLine();
						for (Account acc : accounts) {
							if (id==acc.getId()) {
								System.out.println("Is it this account? name= " + acc.getAccountName() + ", balance= " + acc.getBalance() + ", status= " + acc.getStatus());
								System.out.print("[y] / [n]");
								String yesno = scan.nextLine();
								if (yesno.toLowerCase().equals("y")) {
									System.out.println("Amount to be transferred: ");
									System.out.print("Amount: ");
									amount = scan.nextDouble();
									scan.nextLine();
									if (accountDAO.transfer(acc, a, amount)) {
										System.out.println("Transfer successful!!!");
										updateAccounts(accounts,accountDAO);
									} else {
										System.out.println("Overdraw Error!!!!");
										updateAccounts(accounts,accountDAO);
									}
								}
							}
						}
						
						break;
						
					case 6 :
						modifyUser();
						break;
						
					case 7 :
						BankManagement.runBank();
						break;
						
					default :
						updateAccounts(accounts, accountDAO);
					}
					
				}
			}
			
		}
		
	}
