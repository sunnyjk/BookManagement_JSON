package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class BookAddServlet
 */
@WebServlet("/addBook")
public class BookAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 입력받고
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String date = request.getParameter("date");
		String page = request.getParameter("page");
		String price = request.getParameter("price");
		String author = request.getParameter("author");
		String trans = request.getParameter("trans");
		String sup = request.getParameter("sup");
		String pub = request.getParameter("pub");
		String img = request.getParameter("img");
		String callback = request.getParameter("callback");
		
		// 2. 로직처리
		BookService service = new BookService();
		Boolean result = service.addBook(isbn, title, date, page, price, author, trans, sup, pub, img);

		System.out.println("[Book: Add Servlet] result: " + result);		
		
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
