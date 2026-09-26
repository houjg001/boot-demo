package org.example.demo.db;

import com.github.pagehelper.PageInfo;
import org.example.demo.db.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.UserMapstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@SpringBootTest
public class BootDemoDBApplicationTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {

    }

    @Test
    void pageQuery() {
        PageInfo<UserDto> userDtoPageInfo = userService.queryUsers();
        objectMapper.writeValue(System.out, userDtoPageInfo);
    }

    @Test
    void selectUserByUserId() {
        User user = userService.findById(3L);
//        objectMapper.writeValue(System.out, user);

        UserDto userDto = UserMapstruct.INSTANCE.entityToDto(user);
        objectMapper.writeValue(System.out, userDto);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("test");
        user.setBirthday(LocalDate.of(1978,5, 17));
        user.setEmail("188@139.com");
        user = userService.save(user);
        objectMapper.writeValue(System.out, user);
    }

    @Test
    void saveUserWithAddress() {
        String userJson = """
                {
                    "username": "刘五哥",
                    "birthday": "1989-04-19",
                    "email": "lwg@sohu.com",
                    "addresses": [
                        {
                            "addr": "开发区和平路3号魔法小区4号楼2单元1109",
                            "postcode": "310102"
                        },
                        {
                            
                            "postcode": "310003"
                        }
                    ]
                }
                """;
        UserDto userDto = objectMapper.readerFor(UserDto.class).readValue(userJson);
        User user = userService.saveUserWithAddress(userDto);
        Assert.notNull(user.getUserId(),"用户id");
    }

}
