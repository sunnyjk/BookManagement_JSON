package com.cjon.book.service;

import com.cjon.book.dao.UserDAO;

public class UserService {

	public boolean addUser(String id, String pw, String name) {
		UserDAO dao = new UserDAO();
		return dao.insert(id, pw, name);
	}

	public Boolean login(String id, String pw) {

		UserDAO dao = new UserDAO();
		String dbpw = dao.login(id);
		
		if(pw == dbpw){
			return true;
		}
		
		return false;
	}

}
