package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.RsEventPO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 23:17
 */
@Repository
public interface RsRepository extends CrudRepository<RsEventPO,Integer> {
    @Override
    List<RsEventPO> findAll();


    @Transactional
    @Modifying
    @Query("update RsEventPO rs set rs.eventName = ?1, rs.keyWord = ?2 where rs.id = ?3")
    void updateRsEventById(String eventName, String keyWord, Integer rsEventId);

}
