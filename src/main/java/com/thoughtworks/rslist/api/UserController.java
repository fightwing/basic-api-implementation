package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Service.UserService;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.Error;

import com.thoughtworks.rslist.po.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Boyu Yuan
 * @date 2020/9/16 14:07
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    List<User> userList = initRsUserList();

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private List<User> initRsUserList() {
        User user =new User("Bob", "male", 18,"a@b.com","12345678911");
        List<User> userList1 = new ArrayList<>();
        userList1.add(user);
        return userList1;
    }

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user){

        if (!userService.isUserNameExist(user)){
            userService.addUserPO(userService.userToUserPO(user));
        }
    }

    @GetMapping("/users")
    public List<UserPO> getUserList(){
        return userService.getUserPOs();
    }


    @GetMapping("/user/{id}")
    public UserPO getOneUserPO(@PathVariable Integer id){
        UserPO userPO = userService.getOneUser(id);
        return userPO;
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteOneUser(@PathVariable Integer id){
        try {
            userService.deleteOneUser(id);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity( HttpStatus.OK);
    }


    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity rsExceptionHandler(Exception e){

        String errorMessage;
        if (e instanceof MethodArgumentNotValidException){
            errorMessage = "invalid user";
        }else {
            errorMessage = e.getMessage();
        }
        log.info("测试输出异常");
        log.error(errorMessage);

        Error error = new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);

    }

}
