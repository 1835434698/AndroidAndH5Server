package com.tang.service;

import java.util.List;

import com.tang.bean.User;


public interface UserService {
public abstract void save(User uer);
	
	public abstract void update(User uer);
	
	public abstract boolean query(User uer);
	
	public abstract void delete(User uer);
	

}
