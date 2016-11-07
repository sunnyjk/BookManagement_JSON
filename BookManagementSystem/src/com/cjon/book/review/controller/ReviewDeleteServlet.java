package com.cjon.book.review.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cjon.book.service.BookService;
import com.cjon.book.service.ReviewService;

/**
 * Servlet implementation class BookDeleteServlet
 */
@WebServlet("/reviewDelete")
public class ReviewDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String isbn = request.getParameter("isbn");
		String reviewId = request.getParameter("reviewId");
		String date = request.getParameter("date");
		String callback = request.getParameter("callback");

		boolean result;

		if (session.getAttribute("ID").equals(reviewId)) {
			ReviewService service = new ReviewService();
			result = service.deleteReview(isbn, reviewId, date);

		} else {
			result = false;
		}
		
		System.out.println("[Review: Delete Servlet] result: " + result);		

		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.print(callback + "(" + result + ")");
		out.flush();
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
