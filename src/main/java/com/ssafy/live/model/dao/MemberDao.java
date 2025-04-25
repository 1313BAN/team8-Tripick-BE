package com.ssafy.live.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.live.model.dto.MemberDto;

@Mapper
public interface MemberDao {

    int insertMember(MemberDto member);

    int updateMember(MemberDto member);

    int deleteMember(@Param("id") int id);

    int deleteByEmail(@Param("email") String email);

    MemberDto login(@Param("email") String email, @Param("password") String password);

    MemberDto findByEmail(@Param("email") String email);
}
