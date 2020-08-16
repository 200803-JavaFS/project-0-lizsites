package com.revature.services;

import java.util.Scanner;
import java.util.Set;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class EmployeeService {
	
	private String role = "employee";

	public boolean validateAccount (User u) {
		Scanner scan = new Scanner(System.in);
		System.out.println("New account applications!");
		System.out.println("Name : " + u.getFirstName() + " " + u.getLastName() );
		System.out.println("Username : " +u.getUserName());
		
		System.out.println("validate?");
		System.out.println("Yes / No ?");
		String validate = scan.nextLine().toLowerCase();
		if (validate.equals("yes")) {
			UserDAO userDao = DAOUtilities.getUserDAO();
			userDao.addUser(u);
			return true;
		} else {
			System.out.println(u.getFirstName() + " will not be validated.");
			return false;
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
