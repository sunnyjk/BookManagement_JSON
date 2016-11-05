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
		System.out.println("ShareDAO: " + keyword);
		
		try {
			String sql = "select bisbn, btitle, bauthor, bimgbase64, share, uid from share "
					+ "where btitle like ?";
			
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			
			System.out.println("share select q: " + sql + keyword);
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("share", rs.getString("share"));
				obj.put("id", rs.getString("uid"));
				
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

	public Boolean updateStatus(String isbn, String id) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		Boolean result = false;
		try {
			String sql = "update share set share=?, uId=? where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "true");
			pstmt.setString(2, id);
			pstmt.setString(3, isbn);
			
			System.out.println("Share DAO: " + sql + isbn);

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
				result = true;
				System.out.println("Sharing update result: " + result);


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
