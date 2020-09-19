package com.thoughtworks.rslist;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Service.RsService;
import com.thoughtworks.rslist.Service.UserService;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;
    @Autowired
    RsService rsService;
    @Autowired
    UserRepository userRepository;

//    @BeforeEach
//    public void init(){
//        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
//    }

    @Test
    void should_get_rs_list_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyWord", is("食品")))
                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$[1].keyWord", is("经济")))
                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$[2].keyWord", is("医药")))
                .andExpect(jsonPath("$[2]",not(hasKey("user"))));
    }

    @Test
    void should_get_one_rs_list_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$.keyWord", is("食品")))
                .andExpect(jsonPath("$",not(hasKey("user"))));
        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("股市崩盘了")))
                .andExpect(jsonPath("$.keyWord", is("经济")))
                .andExpect(jsonPath("$",not(hasKey("user"))));
        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("疫苗上市了")))
                .andExpect(jsonPath("$.keyWord", is("医药")))
                .andExpect(jsonPath("$",not(hasKey("user"))));

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
    void should_return_false_when_add_one_event() throws Exception {
        User user =new User("Bob", "male", 18,"a@b.com","12345678911");
        RsEvent rsEvent = new RsEvent("xxx", "xxx",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("message","添加失败"));

    }

    @Test
    void should_add_one_event() throws Exception {
        UserPO savedUser = userRepository.save(UserPO.builder().userName("Bob").age(10)
                .gender("male").email("a@b.com").phone("11111111111").voteNum(10).build());
        String jsonString = "{\"eventName\":\"测试更新热搜\",\"keyWord\":\"更新key\",\"userId\":" + savedUser.getId() + "}";

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void should_update_one_RsEvent_name() throws Exception {
        String jsonSting = "{\"eventName\":\"只修改name\",\"keyWord\":null,\"userId\":25}";

        mockMvc.perform(patch("/rs/26").content(jsonSting).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void should_update_one_RsEvent_key() throws Exception {
        String jsonSting = "{\"eventName\":null,\"keyWord\":\"只修改key\",\"userId\":25}";

        mockMvc.perform(patch("/rs/26").content(jsonSting).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    @Test
    void should_update_one_RsEvent_both() throws Exception {

        String jsonSting = "{\"eventName\":\"测试一下子\",\"keyWord\":\"更新key\",\"userId\":25}";

        mockMvc.perform(patch("/rs/26").content(jsonSting).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }


    @Test
    void should_delete_one_RsEvent() throws Exception {
        mockMvc.perform(delete("/rs/delete/3"));

    }

    @Test
    public void should_throw_rs_event_not_valid_exception() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid index")));
    }
    @Test
    public void should_throw_method_argument_not_valid_exception() throws Exception {
        User user =new User("Bobbbbbbbbbb", "male", 18,"a@b.com","12345678911");
        RsEvent rsEvent =new RsEvent("疫情终将结束","信念", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }

    @Test
    void should_throw_invalid_request_param() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid request param")));
    }

    @Test
    void should_add_one_event_to_database() throws Exception {
        User user =new User("Bob", "male", 18,"a@b.com","12345678911");
        RsEvent rsEvent =new RsEvent("印度想挨锤","政治",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index","17"));

    }
}
