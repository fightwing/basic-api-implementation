package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 14:07
 */
@RestController
public class UserController {

List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user){
        userList.add(user);
    }

    @GetMapping("/user")
    public List<User> getUserList(){
        return userList;
    }

}
