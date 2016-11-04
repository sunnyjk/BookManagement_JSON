package com.cjon.book.service;

import com.cjon.book.dao.ReviewDAO;

public class ReviewService {

	public boolean addReview(String isbn, String title, String comments, String id) {
		ReviewDAO dao = new ReviewDAO();
		return dao.insert(isbn, title, comments, id);
	}

	public String showReviewByKeyword(String keyword) {
		ReviewDAO dao = new ReviewDAO();
		return dao.selectByKeyword(keyword);
	}

	public String showReviewByIsbn(String isbn) {
		ReviewDAO dao = new ReviewDAO();
		return dao.selectByIsbn(isbn);
	}

	public boolean deleteReview(String isbn, String reviewId, String date) {
		ReviewDAO dao = new ReviewDAO();
		return dao.delete(isbn, reviewId, date);
	}

}












