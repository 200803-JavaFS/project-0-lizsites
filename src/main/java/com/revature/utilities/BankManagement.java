package com.revature.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImp;
import com.revature.models.Admin;
import com.revature.models.Employee;
import com.revature.models.User;

public class BankManagement {
 
	public static void runBank() {
		UserDAO userDao = DAOUtilities.getUserDAO();
		System.out.println("./startup.sh ");
		System.out.println("The server starts, and you wait awhile");
		Scanner scan = new Scanner (System.in);
		
		System.out.println("----- Z3NAFIDE HAS ENTERED THE CHAT -------");
		System.out.println("ARE YOU A NEW USER?");
		System.out.println("Y/N");
		
		System.out.println("Welcome to Deep Vine Internal, credentials human");
		System.out.println("ENTER YOUR USERNAME");
		String username = scan.nextLine();
		
		User u = userDao.getUserById(username);
		
		Employee e = new Employee();
		e.getAccountsForUser(u);
		e.validateUser(u);
		Admin a = new Admin();
		a.getAccountsForUser(u);
		a.validateUser(u);
		a.modifyUser();
		
		System.out.println(u);
		System.out.println("ENTER YOUR PASSWORD " + u.getUserName());
		String password = scan.nextLine();
		
		if (password.equals(u.getPassword())) {
			System.out.println("Does the meatbag request services?");
			printUserOptions();
		}
	}
	
	public static void printUserOptions() {
		int options = 0;
		List<String> optionList = new ArrayList<>();
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
			System.out.println(options + ". " + s);
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
	
	public void storeChanges(User u) {
		
	}
	
}
