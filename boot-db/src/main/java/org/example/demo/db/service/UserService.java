package org.example.demo.db.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo.db.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.mapper.UserMapper;

public interface UserService extends BaseService<User> {


    PageInfo<UserDto> queryUsers();

    User saveUserWithAddress(UserDto userDto);
}
