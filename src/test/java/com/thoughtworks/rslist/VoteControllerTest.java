package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.VotePO;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Boyu Yuan
 * @date 2020/9/20 1:55
 */
@AutoConfigureMockMvc
@SpringBootTest
public class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void should_vote_succeed() throws Exception {
        Vote vote = new Vote();
        vote.setVoteNum(5);
        vote.setUserId(25);
        vote.setVoteTime(LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/vote/26").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_vote_record() throws Exception {
        for (int i = 0;i < 8;i++){
            VotePO votePO = VotePO.builder().voteNum(i+1).voteTime(LocalDateTime.now()).userId(8).rsEventId(1).build();
            voteRepository.save(votePO);
        }

            mockMvc.perform(get("/voteRecord").param("userId", String.valueOf(8))
                    .param("rsEventId",String.valueOf(1)).param("pageIndex","1"))
                    .andExpect(jsonPath("$",hasSize(5)))
                    .andExpect(jsonPath("$[0].userId",is(8)))
                    .andExpect(jsonPath("$[0].rsEventId",is(1)));
    }

    @Test
    void should_return_voteRecord_the_specified_time_range() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2020,9,19,07,18,54);
        LocalDateTime endTime = LocalDateTime.of(2020,9,21,12,39,46);

        mockMvc.perform(get("/voteRecordInSpecifiedTime").param("startTime", String.valueOf(startTime))
                .param("endTime", String.valueOf(endTime)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(11)));
    }
}
