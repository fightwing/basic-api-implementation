package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.RsEventPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 23:17
 */
public interface RsRepository extends CrudRepository<RsEventPO,Integer> {
    @Override
    List<RsEventPO> findAll();


}
