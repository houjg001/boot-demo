package org.example.demo.db;

import org.example.demo.db.entity.User;
import org.example.demo.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BootDemoDBApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setUsername("刘一手");
        user.setEmail("lys@qq.com");
        userRepository.save(user);
    }

    @Test
    public void saveUsers() {
        User user1 = new User("张三", LocalDate.of(1988, 4, 23), "aa@163.com");
        User user2 = new User("张丽红", LocalDate.of(1989, 6, 18), "bbd@hotmail.com");

        userRepository.saveAll(List.of(user1, user2));
    }

    @Test
    public void findUser() {
//        List<User> users = userRepository.findByUsername("刘一手");
//        List<User> users = userRepository.findByBirthdayBetweenAndEmailContains(
//                LocalDate.of(1985, 1, 1),
//                LocalDate.of(1990, 1, 1),
//                "qq");
        User user = new User();
        user.setEmail("hotmail");
        Example<User> example = Example.of(user, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        List<User> users = userRepository.findAll(example);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerFor(List.class).writeValue(System.out, users);
    }
}
