package com.thoughtworks.rslist;

import com.thoughtworks.rslist.Service.UserService;
import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:56
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void should_return_false_when_userName_is_not_exist(){

        User user = new User("Tom", "male", 18,"a@b.com","123456789111111");
        Boolean flag = userService.isUserNameExist(user);
        assertEquals(false,flag);
    }
    @Test
    void should_return_true_when_userName_is_not_exist(){

        User user = new User("Bob", "male", 18,"a@b.com","123456789111111");
        Boolean flag = userService.isUserNameExist(user);
        assertEquals(true,flag);
    }

    @Test
    void should_return_id(){
        Integer id = userService.findIdByName("waahh");
        assertEquals(7,id);
    }


}
