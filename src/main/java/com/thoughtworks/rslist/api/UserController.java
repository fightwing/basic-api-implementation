package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 14:07
 */
@RestController
public class UserController {

    List<User> userList = initRsUserList();

    private List<User> initRsUserList() {
        User user =new User("Bob", "male", 18,"a@b.com","12345678911");
        List<User> userList1 = new ArrayList<>();
        userList1.add(user);
        return userList1;
    }

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user){
        if (!isUserNameExist(user)){
            userList.add(user);
        }
    }

    @GetMapping("/user")
    public List<User> getUserList(){
        return userList;
    }

    public boolean isUserNameExist(User user){
         List<User> userList1 = userList.stream().filter(User -> User.getName().equals(user.getName())).collect(Collectors.toList());
        if (userList1.size() == 0) {
            return false;
        }else {
            return true;
        }
    }

}
