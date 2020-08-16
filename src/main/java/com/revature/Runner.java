package com.revature;

import java.sql.Driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AccountDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.BankManagement;
import com.revature.utilities.DAOUtilities;

public class Runner {
	public static void main (String[] args) {
		BankManagement.runBank();

	}
}

