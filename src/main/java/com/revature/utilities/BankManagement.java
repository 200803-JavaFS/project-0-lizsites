package com.revature.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImp;
import com.revature.models.User;

public class BankManagement {
 
	public static void runBank() {
		UserDAO userDao = DAOUtilities.getUserDAO();
		System.out.println("You open a double-encrypted terminal on your burner laptop");
		System.out.println("./startup.sh ");
		System.out.println("The server starts, and you wait awhile");
		Scanner scan = new Scanner (System.in);
		scan.nextLine();
		System.out.println("----- Z3NAFIDE HAS ENTERED THE CHAT -------");
		System.out.println("Welcome to Deep Vine Internal, credentials human");
		System.out.println("ENTER YOUR PASSWORD");
		String password = scan.nextLine();
		User u = userDao.checkPassword(password);
		if (u.getUserName() != null) {
			System.out.println(u);
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
}
