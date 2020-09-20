package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.VotePO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Boyu Yuan
 * @date 2020/9/20 1:54
 */
@Repository
public interface VoteRepository extends CrudRepository<VotePO,Integer> {

}
