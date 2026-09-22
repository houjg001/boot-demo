package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo.db.dto.UserDto;
import org.example.demo.db.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    List<UserDto> selectUserWithAddressByUserId(Long userId);
}