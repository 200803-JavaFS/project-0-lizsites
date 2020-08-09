package com.revature.models;

public class User {
	private String userName;
	private double balance;
	private String name;
	private String password;
	
	
	public User() {
		super();
	}
	public User(String userName, double balance, String name, String password) {
		super();
		this.userName = userName;
		this.balance = balance;
		this.name = name;
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String toString() { 
		return "You are a meatbag named " + this.name + ", alias : " + this.userName + ", balance ; " + this.balance + ", password : " + this.password;
	}
	
	
}
