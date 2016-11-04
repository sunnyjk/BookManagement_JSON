package com.cjon.book.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cjon.book.service.BookService;
import com.cjon.book.service.UserService;

/**
 * Servlet implementation class BookDeleteServlet
 */
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String callback = request.getParameter("callback");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		System.out.println("로그인 서블릿 들어옴");
		
		UserService service = new UserService();
		boolean result = service.login(id, pw);
		
		if(result) {
			// 세션객체를 서버로 부터 하나 할당받아요!!
			HttpSession session = request.getSession(true);	// session 값이 있으면 그 값을 가져온다.
			session.setAttribute("ID", id);
			System.out.println("session generated!");
		}
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.print(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
