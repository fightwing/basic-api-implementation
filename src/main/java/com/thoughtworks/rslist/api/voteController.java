package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Service.RsService;
import com.thoughtworks.rslist.Service.UserService;
import com.thoughtworks.rslist.Service.VoteService;
import com.thoughtworks.rslist.domain.Vote;

import com.thoughtworks.rslist.po.UserPO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
    public ResponseEntity voteForRsEvent(@PathVariable Integer rsEventId, @RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Vote vote = objectMapper.readValue(jsonString,Vote.class);
        UserPO userPO = userService.findById(vote.getUserId());
        if (userPO.getVoteNum() >= vote.getVoteNum()){
            voteService.vote(vote.getVoteNum(),vote.getUserId(),vote.getVoteTime(),rsEventId);
            return ResponseEntity.ok(null);
        }else {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(status);
        }
    }

    @GetMapping("/voteRecord")
    public ResponseEntity<List<Vote>> getVoteRecord(@RequestParam int userId, @RequestParam int rsEventId,@RequestParam int pageIndex){
        Pageable pageable = PageRequest.of(pageIndex-1,5);
        List<Vote> votes = voteService.findAllByUserAndRsEventId(userId,rsEventId,pageable);
        return ResponseEntity.ok(votes);
    }

}
