package com.revature.models;

import java.io.Serializable;

public class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*This is the primary key*/
	private int id;
	private String accountName;
	private String status;
	private double balance;
	
	
	

	public Account(int id, String accountName, String status, double balance) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.status = status;
		this.balance = balance;
	}


	public Account(String accountName, double balance) {
		super();
		this.accountName = accountName;
		this.balance = balance;
		this.status = "pending";
	}

	public Account() {
		super();
	}



	public int getId() {
		return id;
	}




	public void setId(int id) {
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
		return "Account [balance=" + balance + ", id=" + id + ", accountName= " + this.accountName + ", status=" + this.status + "]";
	}
}
