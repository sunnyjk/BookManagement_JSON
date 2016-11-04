package com.cjon.book.service;

import com.cjon.book.dao.UserDAO;

public class UserService {

	public boolean addUser(String id, String pw, String name) {
		UserDAO dao = new UserDAO();
		return dao.insert(id, pw, name);
	}

	public boolean login(String id, String pw) {

		UserDAO dao = new UserDAO();
		boolean result = dao.login(id,pw);
		
		
		return result;
	}

}
