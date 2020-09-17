package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.UserPO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:44
 */
public interface UserRepository extends CrudRepository<UserPO,Integer> {
    @Override
    List<UserPO> findAll();

}
