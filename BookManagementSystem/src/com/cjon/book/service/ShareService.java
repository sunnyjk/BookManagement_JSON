package com.cjon.book.service;

import com.cjon.book.dao.ShareDAO;

public class ShareService {

	public String getList(String keyword) {
		ShareDAO dao = new ShareDAO();
		return dao.selectByKeyword(keyword);
	}

	public Boolean rentBook(String isbn, String id) {
		ShareDAO dao = new ShareDAO();
		return dao.updateStatus(isbn, id);
	}

}
