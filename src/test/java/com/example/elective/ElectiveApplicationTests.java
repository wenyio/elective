package com.example.elective;
import java.sql.Timestamp;

import com.example.elective.entity.User;
import com.example.elective.repository.UserRepository;
import com.example.elective.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class ElectiveApplicationTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void initAdmin() {
        User user = new User();
        user.setName("admin");
        user.setNumber("10001");
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        user.setRole((short)0);
        user.setStatus((short)1);
        userRepository.save(user);
    }
}
