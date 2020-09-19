package com.thoughtworks.rslist;

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
        String jsonSting = "{\"voteNum\":5,\"userId\": 25,\"time\":"+ LocalDateTime.now() + "}";
        mockMvc.perform(post("/rs/vote/17").content(jsonSting)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
