package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.po.VotePO;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ResponseEntity vote(int voteNum, int userId, LocalDateTime voteTime, Integer rsEventId) {
        Optional<RsEventPO> rsEventPO = rsRepository.findById(rsEventId);
        Optional<UserPO> userPO = userRepository.findById(userId);
        rsEventPO.get().setVoteNum(voteNum);
        rsRepository.save(rsEventPO.get());
        VotePO votePO = new VotePO();
        votePO.setUserId(userId);
        votePO.setRsEventId(rsEventId);
        votePO.setVoteTime(voteTime);
        voteRepository.save(votePO);



        }else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(status);
        }

    }
}
