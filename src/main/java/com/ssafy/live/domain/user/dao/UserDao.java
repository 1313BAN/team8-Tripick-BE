package com.ssafy.live.domain.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.domain.user.dto.UserDto;

@Mapper
public interface UserDao {
    void insertUser(UserDto user);
    UserDto findByEmail(String email);
    UserDto findById(int id);
    void updateUser(UserDto userDto);
    void deleteUser(int id);
    void updatePassword(int id, String password);

}