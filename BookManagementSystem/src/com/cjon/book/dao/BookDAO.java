package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice " + "from book2 where btitle like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public String update(String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		String result = null;
		try {

			String sql = "update book2 set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);

				String sql2 = "select bisbn, bimgbase64, btitle, bauthor, bprice " + "from book2 where bisbn = ?";
				PreparedStatement pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, isbn);
				ResultSet rs = pstmt2.executeQuery();

				rs.next();

				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));

				result = obj.toJSONString();

				DBTemplate.close(rs);
				DBTemplate.close(pstmt2);

			} else {
				DBTemplate.rollback(con);
				System.out.println("rollback");
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public String detail(String isbn) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bisbn, bdate, bpage, bsupplement, bpublisher " 
					+ "from book2 where bisbn = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();

			rs.next();
			
			JSONObject obj = new JSONObject();
			obj.put("isbn", rs.getString("bisbn"));
			obj.put("date", rs.getString("bdate"));
			obj.put("page", rs.getString("bpage"));
			obj.put("supplement", rs.getString("bsupplement"));
			obj.put("publisher", rs.getString("bpublisher"));

			result = obj.toJSONString();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}

		return result;
	}

	public Boolean insert(String isbn, String title, String date, String page, String price, String author,
			String trans, String sup, String pub, String img) {
		
		if(date.trim() == "") date = "2016-11-03";
		if(page.trim() == "") page = "300";
		if(trans.trim() == "") trans = "없음";
		if(sup.trim() == "") sup = "없음";
		if(pub.trim() == "") pub = "써니미디어(주)";
		if(img.trim() == "") img = "";

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		Boolean result = false;
		try {
			String sql = "insert into book2 (bisbn, btitle, bdate, bpage, bprice, bauthor, btranslator, "
					+ "bsupplement, bpublisher, bimgbase64)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, page);
			pstmt.setString(5, price);
			pstmt.setString(6, author);
			pstmt.setString(7, trans);
			pstmt.setString(8, sup);
			pstmt.setString(9, pub);
			pstmt.setString(10, img);
			

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);	

			} else {
				DBTemplate.rollback(con);
				System.out.println("rollback");
			}
				
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		
		
		return result;
	}

	public Boolean delete(String isbn) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		Boolean result = false;
		try {
			String sql = "delete from book2 where bisbn = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);				

			} else {
				DBTemplate.rollback(con);
				System.out.println("rollback");
			}
				
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		
		return result;
	}

}
