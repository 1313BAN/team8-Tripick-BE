package com.ssafy.live.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.live.model.dao.MemberDao;
import com.ssafy.live.model.dto.MemberDto;
import com.ssafy.live.util.DBUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public boolean addMember(MemberDto member) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.insertMember(member);
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

    @Override
    public boolean updateMember(MemberDto member) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.updateMember(member);
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

    @Override
    public boolean deleteMember(int id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.deleteMember(id);
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

    @Override
    public boolean deleteMemberByEmail(String email) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = memberDao.deleteByEmail(email);
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

    @Override
    public MemberDto login(String email, String password) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            MemberDto member = memberDao.login(email, password);
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

    @Override
    public MemberDto findByEmail(String email) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            return memberDao.findByEmail(email);
        }
    }
}
