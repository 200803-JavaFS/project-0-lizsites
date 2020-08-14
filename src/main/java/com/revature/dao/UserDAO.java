package com.revature.dao;

import java.util.List; 
import java.util.Set;

import com.revature.models.User;

public interface UserDAO {
	
	public Set<User> getAllUsers();
	
	public boolean addUser(User u);
	public boolean updateCredential(User u, String oldId);
	public boolean removeUser(User u);
	
	public User getUserById(String id);
//	public User checkUser(String field, String value);
	
	
}
