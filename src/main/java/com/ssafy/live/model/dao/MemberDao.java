package com.ssafy.live.model.dao;

import java.sql.*;

import com.ssafy.live.model.dto.MemberDto;

public class MemberDao {

    // 🔒 Singleton Pattern
    private static MemberDao instance = new MemberDao();

    private MemberDao() {}

    public static MemberDao getInstance() {
        return instance;
    }

    // 📌 SQL Query Constants
    private static final String SQL_INSERT =
        "INSERT INTO members (username, email, password, phone) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE members SET username = ?, email = ?, password = ?, phone = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID =
        "DELETE FROM members WHERE id = ?";
    private static final String SQL_DELETE_BY_EMAIL =
        "DELETE FROM members WHERE email = ?";
    private static final String SQL_LOGIN =
        "SELECT id, username, email, password, phone, created_at FROM members WHERE email = ? AND password = ?";
    private static final String SQL_FIND_BY_EMAIL = 
    		"SELECT id, username, email, password, phone, created_at FROM members WHERE email = ?";

    // ✅ Insert Member
    public int insertMember(MemberDto member, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getPhone());

            int result = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    member.setId(rs.getInt(1));
                    System.out.println("Inserted member ID: " + member.getId());
                }
            }

            return result;
        }
    }

    // ✅ Update Member
    public int updateMember(MemberDto member, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getPhone());
            pstmt.setInt(5, member.getId());

            return pstmt.executeUpdate();
        }
    }

    // ✅ Delete Member by ID
    public int deleteMember(int id, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    // ✅ Delete Member by Email
    public int deleteByEmail(String email, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_BY_EMAIL)) {
            pstmt.setString(1, email);
            return pstmt.executeUpdate();
        }
    }

    // ✅ Login (Find by Email and Password)
    public MemberDto login(String email, String password, Connection conn) throws SQLException {
        MemberDto member = null;
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_LOGIN)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new MemberDto();
                    member.setId(rs.getInt("id"));
                    member.setUsername(rs.getString("username"));
                    member.setEmail(rs.getString("email"));
                    member.setPassword(rs.getString("password"));
                    member.setPhone(rs.getString("phone"));
                    member.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }

        return member;
    }

	public MemberDto findByEmail(String email, Connection conn) throws SQLException {
		MemberDto member = null;
		// TODO Auto-generated method stub
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {
	        pstmt.setString(1, email);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                member = new MemberDto();
	                member.setId(rs.getInt("id"));
	                member.setUsername(rs.getString("username"));
	                member.setEmail(rs.getString("email"));
	                member.setPassword(rs.getString("password")); // 해싱된 비번
	                member.setPhone(rs.getString("phone"));
	                member.setCreatedAt(rs.getTimestamp("created_at"));
	            }
	        }
	    }

	    return member;
	}
	    
}
