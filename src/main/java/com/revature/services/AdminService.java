package com.revature.services;

import java.util.Scanner;
import java.util.Set;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class AdminService extends EmployeeService {
	
	
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
					
					System.out.println("press [1] for approve/declined");
					System.out.println("press [2] to make a deposit");
					System.out.println("press [3] to make a withdrawal");
					System.out.println("press [4] to make a transfer");
					System.out.println("press [5] to go to Home Menu");
					System.out.println("press [6] to exit Customer Console");
					
					Account updateAccount = new Account();
					
					
					int choice = scan.nextInt();
					scan.nextLine();
					
					switch (choice) {
					case 1 :
						System.out.println("|------------------------------------------|");
						System.out.println("Enter the id of account you would like to modify:");
						int id = scan.nextInt();
						scan.nextLine();
						for (Account b : accounts) {
							if (b.getId()==id) {
								updateAccount = b;
							}
						}
						System.out.println("account= " + updateAccount.getAccountName() + ", balance= $ " +updateAccount.getBalance() +", status= " + updateAccount.getStatus());
						System.out.println("press [1] for approve");
						System.out.println("press [2] to decline/close");
						int approve = scan.nextInt();
						scan.nextLine();
						if (approve==1) {
						updateAccount.setStatus("approved");
						accountDAO.updateAccountStatus(updateAccount);

						} else if (approve == 2) {
						updateAccount.setStatus("declined");
						accountDAO.updateAccountStatus(updateAccount);

						} 
						break;
						
						
					case 2 :
						
						System.out.println("|------------------------------------------|");
						System.out.println("Enter the id of account you would like to modify:");
						int id2 = scan.nextInt();
						scan.nextLine();
						for (Account b : accounts) {
							if (b.getId()==id2) {
								updateAccount = b;
							}
						}
						System.out.println("account= " + updateAccount.getAccountName() + ", balance= $ " +updateAccount.getBalance() +", status= " + updateAccount.getStatus());
						System.out.print("Amount to be Deposited:    ");
						double amount = scan.nextDouble();
						scan.nextLine();
						if (accountDAO.balanceChange(updateAccount, amount)) {
							System.out.println("Deposit successful!!!!");
						} else {
							System.out.println("Overdraw Error!!!!!");
						}

						break;
					case 3 :
						System.out.println("|------------------------------------------|");
						System.out.println("Enter the id of account you would like to modify:");
						int id3 = scan.nextInt();
						scan.nextLine();
						for (Account b : accounts) {
							if (b.getId()==id3) {
								updateAccount = b;
							}
						}
						System.out.println("account= " + updateAccount.getAccountName() + ", balance= $ " +updateAccount.getBalance() +", status= " + updateAccount.getStatus());
						System.out.print("Amount to be Withdrawn: ");
						double withdrawal = scan.nextDouble();
						scan.nextLine();
						accountDAO = DAOUtilities.getAccountDAO();
						if (accountDAO.balanceChange(updateAccount, -withdrawal)) {
							System.out.println("Withdrawal successful!!!!");

						} else {
							System.out.println("Overdraw Error!!!!!!!");

						}
						break;
						
					case 4 :
						System.out.println("|------------------------------------------|");
						System.out.println("Enter the id of account you would like to modify:");
						int id4 = scan.nextInt();
						scan.nextLine();
						for (Account b : accounts) {
							if (b.getId()==id4) {
								updateAccount = b;
							}
						}
						System.out.println("transfering account= " + updateAccount.getAccountName() + ", balance= $ " +updateAccount.getBalance() +", status= " + updateAccount.getStatus());
						
						System.out.println("Please enter the id of the account you would like to transfer to: ");
						System.out.print("Id: ");
						id = scan.nextInt();
						scan.nextLine();
						Account transferAccount = new Account();
						for (Account acc : accounts) {
							if (id==acc.getId()) {
								transferAccount = acc;
							}
						}
						System.out.println("Is it this account? name= " + transferAccount.getAccountName() + ", balance= " + transferAccount.getBalance() + ", status= " + transferAccount.getStatus());
						System.out.print("[y] / [n]");
						String yesno = scan.nextLine();
						if (yesno.toLowerCase().equals("y")) {
							System.out.println("Amount to be transferred: ");
							System.out.print("Amount: ");
							amount = scan.nextDouble();
							scan.nextLine();
							if (accountDAO.transfer(transferAccount, updateAccount, amount)) {
								System.out.println("Transfer successful!!!");
								
							} else {
								System.out.println("Overdraw Error!!!!");
								
							}
						}
						
						break;
						
					case 5 :
						modifyUserWelcomeScreen();
						break;
						
					case 6 :
						BankManagement.runBank();
						break;
						
					default :
						updateAccounts(accounts, accountDAO);
					}
					
				}
			
			
		}
		
	
