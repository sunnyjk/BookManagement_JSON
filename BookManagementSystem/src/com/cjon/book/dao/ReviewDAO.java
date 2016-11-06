package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class ReviewDAO {

	public boolean insert(String isbn, String title, String comments, String id) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		Boolean result = false;
		
		try {
			String sql = "insert into review (bisbn, btitle, comments, uid, date) "
					+ "values (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, comments);
			pstmt.setString(4, id);		
			pstmt.setString(5, new Date().toLocaleString());

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);				

			} else {
				DBTemplate.rollback(con);
				System.out.println("rollback");
			}
				
			System.out.println("insert(): " + result);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		
		
		return result;
		
	}

	public String selectByKeyword(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		System.out.println("ReviewDAO: " + keyword);
		
		try {
			String sql = "select bisbn, btitle, comments, uid, date from review "
					+ "where comments like ?";
			
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("comments", rs.getString("comments"));
				obj.put("id", rs.getString("uid"));
				obj.put("date", rs.getString("date"));
				arr.add(obj);
				System.out.println("Add obj");
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

	public String selectByIsbn(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		System.out.println("ReviewDAO: " + isbn);
		
		try {
			String sql = "select bisbn, btitle, comments, uid, date from review "
					+ "where bisbn = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("comments", rs.getString("comments"));
				obj.put("id", rs.getString("uid"));
				obj.put("date", rs.getString("date"));
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

	public boolean delete(String isbn, String reviewId, String date) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		Boolean result = false;
		
		try {
			String sql = "delete from review where bisbn = ? and uid = ? and date = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, reviewId);
			pstmt.setString(3, date);			

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);				

			} else {
				DBTemplate.rollback(con);
				System.out.println("rollback");
			}
				
			System.out.println("delete(): " + result);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		
		return result;
	}


}
