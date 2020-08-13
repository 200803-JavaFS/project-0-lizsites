package com.revature.models;

public class Account {
	
	/*This is the primary key*/
	private String id;
	private String accountName;
	private String status;
	private double balance;
	
	
	

	public Account(String id, String accountName, String status, double balance) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.status = status;
		this.balance = balance;
	}


	public Account(String id, String accountName, double balance) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.balance = balance;
		this.status = "pending";
	}

	public Account() {
		super();
	}



	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getAccountName() {
		return accountName;
	}




	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public double getBalance() {
		return balance;
	}




	public void setBalance(double balance) {
		this.balance = balance;
	}




	@Override
	public String toString() {
		return "Account [balance=" + balance + ", userid=" + id + ", accountName= " + this.accountName + ", isFrozen=" + this.status + "]";
	}
}
