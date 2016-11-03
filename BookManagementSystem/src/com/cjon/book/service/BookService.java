package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {

	// 리스트를 가져오는 일을 하는 method
	public String getList(String keyword) {
		// 일반적인 로직처리 나와요!!
		
		// 추가적으로 DB처리가 나올 수 있어요!
		// DB처리는 Service에서 처리는하는게 아니라..다른 객체를 이용해서
		// Database처리를 하게 되죠!!
		BookDAO dao = new BookDAO();
		String result = dao.select(keyword);	
		
		return result;
	}

	public String updateBook(String isbn, String title, String author, String price) {
		BookDAO dao = new BookDAO();
		String result = dao.update(isbn, title, author, price);	
		return result;
	}

	public String getBookDetail(String isbn) {
		BookDAO dao = new BookDAO();
		String result = dao.detail(isbn);
		return result;
	}

}












