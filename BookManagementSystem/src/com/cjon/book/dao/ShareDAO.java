package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class ShareDAO {

	public String selectByKeyword(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select bisbn, btitle, bauthor, bimgbase64, share, uid from book2 "
					+ "where btitle like ?";
			
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("share", rs.getString("share"));
				obj.put("id", rs.getString("uid"));
				
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

	public Boolean updateStatus(String isbn, String id) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		Boolean result = false;
		try {
			String sql = "update book2 set share=?, uId=? where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "true");
			pstmt.setString(2, id);
			pstmt.setString(3, isbn);
		

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
				result = true;


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

	public String selectById(String id) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select bisbn, btitle, bauthor, bimgbase64, share, uid from book2 "
					+ "where uid = ?";
			
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("share", rs.getString("share"));
				obj.put("id", rs.getString("uid"));
				
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

	public Boolean updateReturn(String isbn, String id) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		Boolean result = false;
		try {
			String sql = "update book2 set share=?, uId=? where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "false");
			pstmt.setString(2, "");
			pstmt.setString(3, isbn);

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
				result = true;

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
