package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
