package com.ssafy.live.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ssafy.live.model.dao.MemberDao;
import com.ssafy.live.model.dto.MemberDto;
import com.ssafy.live.util.DBUtil;

public class MemberService {

    private MemberDao memberDao = MemberDao.getInstance();

    /**
     * 회원 등록 서비스
     * @param member 등록할 회원 정보
     * @return 성공 여부
     * @throws SQLException 
     */
    public boolean addMember(MemberDto member) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.insertMember(member, conn);
                conn.commit();
                System.out.println("Member added successfully: " + member);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error adding member: " + member);
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 회원 정보 수정 서비스
     * @param member 수정할 회원 정보 (id 필수)
     * @return 성공 여부
     * @throws SQLException 
     */
    public boolean updateMember(MemberDto member) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.updateMember(member, conn);
                conn.commit();
                System.out.println("Member updated successfully: " + member);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error updating member: " + member);
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 회원 삭제 서비스
     * @param id 삭제할 회원의 id
     * @return 성공 여부
     * @throws SQLException 
     */
    public boolean deleteMember(int id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.deleteMember(id, conn);
                conn.commit();
                System.out.println("Member deleted successfully: id=" + id);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error deleting member with id: " + id);
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    /**
     * 로그인 기능: 이메일과 비밀번호로 회원 인증.
     * @param email 회원 이메일
     * @param password 회원 비밀번호 (실제 운영에서는 해시값 비교 권장)
     * @return 인증된 회원 정보 (존재하지 않으면 null 반환)
     * @throws SQLException 
     */
    public MemberDto login(String email, String password) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            MemberDto member = memberDao.login(email, password, conn);
            if (member != null) {
                System.out.println("Member login successful: " + member);
            } else {
                System.out.println("Member login failed for email: " + email);
            }
            return member;
        } catch (SQLException e) {
            System.out.println("Error during login for email: " + email);
            e.printStackTrace();
            throw e;
        }
    }
    
    public boolean deleteMemberByEmail(String email) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.deleteByEmail(email, conn);
                conn.commit();
                System.out.println("Member deleted successfully: email=" + email);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error deleting member with email: " + email);
                e.printStackTrace();
                throw e;
            }
        }
    }

	public MemberDto findByEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		try (Connection conn = DBUtil.getConnection()) {
	        return memberDao.findByEmail(email, conn);
	    }
	}

}
