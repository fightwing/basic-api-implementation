package com.thoughtworks.rslist;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_rs_list_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")));
    }

    @Test
    void should_get_one_rs_list_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$.keyWord", is("食品")));
        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$.keyWord", is("经济")));
        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$.keyWord", is("医药")));

    }

    @Test
    void should_get_one_rs_list_between() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")));
    }

    @Test
    void should_add_one_rs_list() throws Exception {
        RsEvent rsEvent =new RsEvent("疫情终将结束","信念");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")))
                .andExpect(jsonPath("$[3].eventName", is("疫情终将结束")))
                .andExpect(jsonPath("$[3].keyWord", is("信念")));
    }

    @Test
    void should_update_one_RsEvent_name() throws Exception {
        RsEvent rsEvent =new RsEvent("只修改name",null);
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonSting = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/update?index=1").content(jsonSting).contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("只修改name")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")));
    }@Test
    void should_update_one_RsEvent_key() throws Exception {
        RsEvent rsEvent =new RsEvent(null,"只修改key");
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonSting = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/update?index=1").content(jsonSting).contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("只修改key")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")));
    }
    @Test
    void should_update_one_RsEvent_both() throws Exception {
        RsEvent rsEvent =new RsEvent("修改name","也修改key");
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonSting = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/update?index=1").content(jsonSting).contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("修改name")))
                .andExpect(jsonPath("$[0].keyWord", is("也修改key")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")));
    }


    @Test
    void should_delete_one_RsEvent() throws Exception {
        mockMvc.perform(delete("/rs/delete/3"));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")));
    }


}
