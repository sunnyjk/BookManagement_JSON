package com.cjon.book.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.UserService;

/**
 * Servlet implementation class BookUserJoin
 */
@WebServlet("/join")
public class UserJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserJoinServlet() {
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
		String name = request.getParameter("name");
		
		System.out.println("Join Servlet 들어옴!");
		
		boolean result = false;
		
		if(pw != null && name != null){
		
			System.out.println("회원가입 진행하기");
		/*	UserService service = new UserService();
			result = service.addUser(id, pw, name);
		*/
		} else {
			System.out.println("UserJoinServlet: dao에 가서 ID값 중복되는지 확인하기");
			result = true;
		}
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
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
