package com.revature.dao;

import com.revature.models.User;

public interface UserDAO {
	public void addUser(User u);
	public void updateUsername(User u);
	public void balanceChange( User u);
	public User checkPassword(String password);
}
