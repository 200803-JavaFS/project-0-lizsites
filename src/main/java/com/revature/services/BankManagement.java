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
		System.out.println("ENTER YOUR USERNAME:");
		String username = scan.nextLine();
		
		User u = userDao.getUserById(username);
		u.setAccounts(accountDao.searchAccountsByUserId(u.getUserName()));
		if (u!=null){
		
		
		System.out.println("ENTER YOUR PASSWORD:");
		String password = scan.nextLine();
		
		if (password.equals(u.getPassword())) {
			if (u.getRole().equals("admin")) {
				bankLog.info("admin " + u.getUserName() + " has logged in!");
				System.out.println("Would you like to enter the admin console?");
				System.out.print("y/n?     ");
				String adminConsole = scan.nextLine();
				if (adminConsole.toLowerCase().equals("y")) {
					AdminService adminStuff = new AdminService();
					adminStuff.modifyUserWelcomeScreen();
				} 
			} else if (u.getRole().equals("employee")) {
				bankLog.info("employee " + u.getUserName() + " has logged in!");
				System.out.println("Would you like to enter the employee console?");
				System.out.print("y/n?     ");
				String empConsole = scan.nextLine();
				if (empConsole.toLowerCase().equals("y")) {
					EmployeeService empStuff = new EmployeeService();
					empStuff.modifyUserWelcomeScreen();
				} 
			}
			bankLog.info("user has logged in!");
			printUserOptions(u);
		}
	} 
		System.out.println("about to exit!");
		System.out.println("Would you like to abort program?");
		System.out.println("Enter \"y\" to answer \"yes\" and exit.");
		String answer = scan.nextLine();
		if (answer.toLowerCase().equals("y")) {
			System.out.println("have a good day!");
		} else {
			login();
		}
		
	}
	
	
	public static void printUserOptions(User u) {
		int options = 0;
		List<String> optionList = new ArrayList<>();
		System.out.println("Available acounts:");
		for (Account a : u.getAccounts()) {
			System.out.println(a);
		}
		
		String option1 = "Apply for a New Account";
		String option2 = "Deposit";
		String option3 = "WithDraw";
		String option4 = "Transfer";
		String option5 = "Add companion";
		String option6 = "exit";
		
		
		
		optionList.add(option1);
		optionList.add(option2);
		optionList.add(option3);
		optionList.add(option4);
		optionList.add(option5);
		optionList.add(option6);
		for (String s : optionList) {
			options++;
			System.out.println("[" +options +"]" + ". " + s);
		}
		String option = scan.nextLine();
		switch (option) {
		case  "1" : 
			System.out.println("What name would you like to give your account?");
			String name = scan.nextLine();
			System.out.println("What is the initial deposit you'd like to make?");
			double deposit = scan.nextDouble();
			scan.nextLine();
			Account newAccount = new Account(name,deposit);
			if (accountDao.testFunction(newAccount,u)) {
				System.out.println("New account applied for!!");
				u.getAccounts().add(newAccount);
			}
			printUserOptions(u);
			break;
		case  "2" :
			System.out.println("Select Account by Id");
			int choice = scan.nextInt();
			scan.nextLine();
			Account depAcc = new Account();
			for (Account a : u.getAccounts()) {
				if (a.getId()==choice) {
				depAcc = a;
				}
			}
			
				if (depAcc.getStatus().equals("approved")) {
					System.out.println("Amount to be Deposited:");
					String depositString = scan.nextLine();
					double amount = Double.parseDouble(depositString.replace("-", ""));
					accountDao.balanceChange(depAcc, amount);
					
				} else if (depAcc.getStatus().equals("pending") || depAcc.getStatus().equals("declined")){
						System.out.println("APOLOGIES! ACCOUNT CANNOT BE UPDATED UNTIL IT IS APPROVED!");
				} else {
					System.out.println("Incorrect input!!");
				}
			
		
			printUserOptions(u);
			break;
		case  "3" :
			System.out.println("Select Account by Id");
			String choice2 = scan.nextLine();
			int choice2num = Integer.parseUnsignedInt(choice2);
			Account withAcc = new Account();
			for (Account a : u.getAccounts()) {
				if (a.getId()==choice2num) {
					withAcc = a;
				}
			}
				if (withAcc.getStatus().equals("approved")) {
				System.out.println("Amount to be WithDrawn:");
				String amountString = scan.nextLine();
				double amount = Double.parseDouble(amountString.replace("-", ""));
			
				accountDao.balanceChange(withAcc, -amount);
				} else if (withAcc.getStatus().equals("pending") || withAcc.getStatus().equals("declined")){
					System.out.println("APOLOGIES! ACCOUNT CANNOT BE UPDATED UNTIL IT IS APPROVED!");
				} else {
					System.out.println("Incorrect input!!");
				}
			
			printUserOptions(u);
			break;
		case  "4" :
			System.out.println("Input the id of your account you'd like to start transfer with:");
			for (Account a : u.getAccounts()) {
				System.out.println(a);
			}
			String choice3string = scan.nextLine();
			int choice3 = Integer.parseUnsignedInt(choice3string);
			Account transferAccount = new Account();
			for (Account a : u.getAccounts()) {
				if (choice3 == a.getId()) {
					transferAccount = a;
					
				}
			}
			if (transferAccount.getStatus().equals("approved")) {
				System.out.println("this account?    " + transferAccount.getAccountName() + ", balance=" + transferAccount.getBalance());
				System.out.println("Y/N?");
				String answer = scan.nextLine();
				if (answer.toLowerCase().equals("y")) {
				System.out.println("Enter the id of an account you'd like to transfer to:");
				String choice4string = scan.nextLine();
				int choice4 = Integer.parseUnsignedInt(choice4string);
				Account target = accountDao.getAccountBySerial(choice4);
				for (Account a : u.getAccounts()) {
					if (target.getId() == a.getId()) {
						target = a;
					}
				}
				if (target.getStatus().equals("approved")) {
				System.out.println("this account?    " + target.getAccountName());
				System.out.println("Y/N?");
				String answer2 = scan.nextLine();
				if (answer.toLowerCase().equals("y")) {
					System.out.println("Amount to be Transferred:");
					String transferString = scan.nextLine();
					double transfer = Double.parseDouble(transferString.replace("-", ""));
					if(accountDao.transfer(target,transferAccount,transfer)) {
						
					}
				}
				} else if (target.getStatus().equals("pending")||target.getStatus().equals("declined")) {
					System.out.println("APOLOGIES BUT TARGET ACCOUNT MAY NOT ACCEPT TRANSFERS UNTIL ACCOUNT IS APPROVED");
				} else {
					System.out.println("Incorrect Input:");
				}
				}
			} else if (transferAccount.getStatus().equals("pending")||transferAccount.getStatus().equals("declined")) {
				System.out.println("APOLOGIES BUT YOU MAY NOT MAKE A TRANSFER UNTIL ACCOUNT IS APPROVED");
				
			} 
				
			
			printUserOptions(u);
			break;
		case  "5" :
			for (User user : userDao.getAllUsers()) {
				System.out.println("username = " + user.getUserName() + ", name= " + user.getFirstName() + " " + user.getLastName());
			}
			System.out.println("Input the username of a user you would like to make a joint account with: ");
			String username = scan.nextLine();
			User jointUser = userDao.getUserById(username);
			for (Account acc : u.getAccounts()) {
				System.out.println(acc);
			}
			System.out.println("Enter account id of account you would to join:");
			int jointId = scan.nextInt();
			scan.nextLine();
			for (Account acc : u.getAccounts()) {
				if (jointId==acc.getId()) {
					System.out.println("Applying for a Joint Account!");
					accountDao.addUserToAccount(acc, jointUser);
				}
			
			}
			printUserOptions(u);
			break;
		case  "6" :
			
			break;
		default :
		}
		
	}
	
	
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
