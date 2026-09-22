package org.example.demo.db;

import org.example.demo.db.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BootDemoDBApplicationTest {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void selectUserWithAddressByUserId() {
        List<UserDto> userDtoList = userMapper.selectUserWithAddressByUserId(2L);
        objectMapper.writeValue(System.out, userDtoList);
    }
}
