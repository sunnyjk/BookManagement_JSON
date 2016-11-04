package com.cjon.book.review.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cjon.book.service.ReviewService;

/**
 * Servlet implementation class BookAddServlet
 */
@WebServlet("/review")
public class ReviewAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewAddServlet() {
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
		
		String callback = request.getParameter("callback");
		boolean result = false;
		
		System.out.println("들어와야됨");
		
		if(session.getAttribute("ID") != null){
			// 1. 입력받고
			String isbn = request.getParameter("isbn");
			String title = request.getParameter("title");
			String comments = request.getParameter("comments");
			String id = (String) session.getAttribute("ID");
			
			System.out.println("review: " + isbn + " / " + comments + "/ id: " + id);
			
			// 2. 로직처리
			ReviewService service = new ReviewService();
			result = service.addReview(isbn, title, comments, id);
			
		} else{
			
		}		

		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
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