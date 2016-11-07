package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjon.book.common.DBTemplate;

public class UserDAO {

	public boolean insert(String id, String pw, String name) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		Boolean result = false;
		
		try {
			String sql = "insert into user (uid, upw, uname) "
					+ "values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);			

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

	public boolean login(String id, String pw) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			String sql = "select uid from user where uid = ? and upw=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);	
			pstmt.setString(2, pw);	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = true;
			} else{
				result = false;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		
		return result;
	}

	public boolean select(String id) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = true;
		
		try {
			String sql = "select uid from user where uid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = false;
			}
			
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
