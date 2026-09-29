package org.example.demo.db.service;

import com.github.pagehelper.PageInfo;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;

import java.util.Map;

public interface UserService extends BaseService<User> {


    PageInfo<UserDto> queryUsers(Map<String,Object> param, int pageNum, int pageSize);

    User saveUserWithAddress(UserDto userDto);
}
