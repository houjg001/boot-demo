package org.example.demo.db;

import com.github.pagehelper.PageInfo;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.UserMapstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BootDemoDBApplicationTest {

    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void pageQuery() {
        Map<String,Object> param = new HashMap<>();
        param.put("email", "syp@icloud.com");
        PageInfo<UserDto> userDtoPageInfo = userService.queryUsers(param, 0, 0);
        objectMapper.writerFor(PageInfo.class).writeValue(System.out,userDtoPageInfo);
    }

    @Test
    void selectUserByUserId() {
        User user = userService.findById(3L);
//        objectMapper.writeValue(System.out, user);

        UserDto userDto = UserMapstruct.INSTANCE.entityToDto(user);

    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUserName("test");
        user.setBirthday(LocalDate.of(1978,5, 17));
        user.setEmail("188@139.com");
        user = userService.save(user);

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
    }

}
