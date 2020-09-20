package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:47
 */
@Service
public class UserService {


   final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean isUserExist(int userId){
        Optional<UserPO> user = userRepository.findById(userId);
        if (user.get() != null){
            return true;
        }else {
            return false;
        }

    }

    public UserPO userToUserPO(User user){
        UserPO userPO = new UserPO();
        userPO.setUserName(user.getName());
        userPO.setGender(user.getGender());
        userPO.setAge(user.getAge());
        userPO.setEmail(user.getEmail());
        userPO.setPhone(user.getPhone());

//        UserPO userPO1 = UserPO.builder().userName(user.getName()).gender(user.getGender()).age(user.getAge())
//                .email(user.getEmail()).phone(user.getPhone()).voteNum(user.getVoteNum()).build();
        return userPO;
    }

    public void addUserPO(UserPO userPO){

         userRepository.save(userPO);

    }

    public List<UserPO> getUserPOs(){
        return userRepository.findAll();
    }

    public UserPO getOneUser(Integer id){
        return userRepository.findById(id).get();
    }

    public void deleteOneUser(Integer id){
        userRepository.deleteById(id);
    }

    public Integer findIdByName(String name){
        UserPO userPO = userRepository.findByUserName(name);
        if (userPO != null){
            return userPO.getId();
        }else {
            return 0;
        }
    }

    public UserPO findById(Integer userId){
        return userRepository.findById(userId).get();
    }
}
