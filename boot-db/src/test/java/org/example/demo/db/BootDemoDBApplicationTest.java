package org.example.demo.db;

import com.github.pagehelper.PageInfo;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.UserMapstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BootDemoDBApplicationTest {

    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*@Test
    void dbInit() throws IOException {
        jdbcTemplate.execute("TRUNCATE TABLE t_users");
        jdbcTemplate.execute("TRUNCATE TABLE t_addresses");
        jdbcTemplate.execute("TRUNCATE TABLE t_users_addresses");

        Resource resource = resourceLoader.getResource("classpath:users.json");
        InputStream inputStream = resource.getInputStream();
        UserDto[] userDtos = objectMapper.readValue(inputStream, UserDto[].class);
        for (UserDto userDto : userDtos) {
            userService.saveUserWithAddress(userDto);
        }
    }*/

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

    /*@Test
    void saveUser() {
        User user = new User();
        user.setUserName("test");
        user.setBirthday(LocalDate.of(1978,5, 17));
        user.setEmail("188@139.com");
        user = userService.save(user);

    }*/

    @Test
    void saveUserWithAddress() {
        String userJson = """
                {
                    "userName": "刘五哥",
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
