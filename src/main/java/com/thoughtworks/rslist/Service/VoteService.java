package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.po.VotePO;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Boyu Yuan
 * @date 2020/9/20 1:54
 */
@Service
public class VoteService {

    @Autowired
    RsRepository rsRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;

    public void vote(int voteNum, int userId, LocalDateTime voteTime, Integer rsEventId) {
        Optional<RsEventPO> rsEventPO = rsRepository.findById(rsEventId);
        rsEventPO.get().setVoteNum(voteNum);
        rsRepository.save(rsEventPO.get());
        VotePO votePO = new VotePO();
        votePO.setUserId(userId);
        votePO.setRsEventId(rsEventId);
        votePO.setVoteTime(voteTime);
        votePO.setVoteNum(voteNum);
        voteRepository.save(votePO);
    }

    public Vote votePOToVote(VotePO votePO){
        Vote vote = Vote.builder().userId(votePO.getUserId()).rsEventId(votePO.getRsEventId()).voteTime(votePO.getVoteTime())
                .voteNum(votePO.getVoteNum()).build();
        return vote;
    }

    public List<Vote> findAllByUserAndRsEventId(int userId, int rsEventId, Pageable pageable){
        List<VotePO> votePOS = voteRepository.findAllByUserIdAndRsEventId(userId, rsEventId, pageable);
        List<Vote> votes = new ArrayList<>();
        for (VotePO votePO : votePOS){
            votes.add(votePOToVote(votePO));
        }
        return votes;
    }

    public List<Vote> findAllInSpecifiedTimeRange(LocalDateTime startTime, LocalDateTime endTime){
        List<VotePO> votePOS = voteRepository.findAll();
        System.out.println(startTime);
        List<Vote> votes = new ArrayList<>();
        for (VotePO votePO : votePOS){
            if (votePO.getVoteTime().isAfter(startTime)&&votePO.getVoteTime().isBefore(endTime)){
                votes.add(votePOToVote(votePO));
            }
        }
        return votes;
    }
}
