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
			String sql = "select bisbn, bimgurl, btitle, bauthor, bprice " + "from book where btitle like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgurl"));
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
			System.out.println(isbn);
			System.out.println(price);
			String sql = "update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
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

				System.out.println("count 후 commit!!");
				String sql2 = "select bisbn, bimgurl, btitle, bauthor, bprice " + "from book where bisbn = ?";
				PreparedStatement pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, isbn);
				ResultSet rs = pstmt2.executeQuery();

				System.out.println("sql finished");
				System.out.println(rs);

				rs.next();

				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgurl"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));

				result = obj.toJSONString();
				System.out.println(result);

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
					+ "from book where bisbn = ?";
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
			System.out.println("detail(): " + result);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}

		return result;
	}

}
