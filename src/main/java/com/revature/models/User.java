package com.revature.models;


import java.util.Set;

public class User {
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	private Set<Account> accounts;

	
	
	public User() {
		super();
	}

	

	public User(String userName, String firstName, String lastName, String password, String role,
			Set<Account> accounts) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.accounts = accounts;
	}
	
	


	@Override
	public String toString() {
		return "User [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", role=" + role + ", accounts=" + accounts + "]";
	}



	public User(String userName, String firstName, String lastName, String password) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = "user";
	}



	public User(String userName, String firstName, String lastName, String password, String role) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public Set<Account> getAccounts() {
		return accounts;
	}



	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	
	
	
}
