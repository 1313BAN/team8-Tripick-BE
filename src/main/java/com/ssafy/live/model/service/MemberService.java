package com.ssafy.live.model.service;

import java.sql.SQLException;
import com.ssafy.live.model.dto.MemberDto;

public interface MemberService {

    boolean addMember(MemberDto member) throws SQLException;

    boolean updateMember(MemberDto member) throws SQLException;

    boolean deleteMember(int id) throws SQLException;

    boolean deleteMemberByEmail(String email) throws SQLException;

    MemberDto login(String email, String password) throws SQLException;

    MemberDto findByEmail(String email) throws SQLException;
}
