package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.VotePO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Boyu Yuan
 * @date 2020/9/20 1:54
 */
@Repository
public interface VoteRepository extends PagingAndSortingRepository<VotePO,Integer> {

    @Override
    List<VotePO> findAll();

    List<VotePO> findAllByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);
}
