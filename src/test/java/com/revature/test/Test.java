package com.revature.test;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;

import com.revature.dao.UserDAO;
import com.revature.models.User;
import com.revature.utilities.DAOUtilities;

public class Test {
	public static void main (String[] args) {
		
		
		User u = new User("dan123" , 30.00 , "Daniella" , "dovetail");
		UserDAO userDAO = DAOUtilities.getUserDAO();
		userDAO.addUser(u);
		
		
	}
}
