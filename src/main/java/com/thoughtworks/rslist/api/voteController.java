package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Service.RsService;
import com.thoughtworks.rslist.Service.UserService;
import com.thoughtworks.rslist.Service.VoteService;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.repository.RsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Boyu Yuan
 * @date 2020/9/20 1:52
 */
@RestController
public class voteController {

    @Autowired
    VoteService voteService;
    @Autowired
    UserService userService;
    @Autowired
    RsService rsService;




    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity voteForRsEvent(@PathVariable Integer rsEventId, @RequestBody Vote vote){
        RsEventPO rsEventPO = rsService.findById(rsEventId);
        Optional<UserPO> userPO = userService.findById(userId);
        if (userPO.get().getVoteNum() >= vote.getVoteNum()){
            voteService.vote(vote.getVoteNum(),vote.getUserId(),vote.getVoteTime(),rsEventId);
            return ResponseEntity.ok(null);
        }else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(status);
        }
    }

}
