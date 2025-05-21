package com.ssafy.live.domain.user.dao;

import com.ssafy.live.domain.user.dto.UserDetailDto;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.domain.user.dto.UserUpdateDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    void insertUser(UserDto user);

    UserDto findByEmail(String email);

    UserDto findById(int id);

    UserDetailDto selectUserDetail(int id);

    void updateUser(UserUpdateDto user);

    void deleteUser(int id);

    void updatePassword(@Param("id") int id, @Param("password") String password);

    void insertUserMotive(@Param("userId") int userId, @Param("motiveCode") int motiveCode);

    void deleteUserMotives(int userId);

    List<Integer> findUserMotiveCodes(int userId);

    List<String> findUserMotiveNames(int userId);
}
