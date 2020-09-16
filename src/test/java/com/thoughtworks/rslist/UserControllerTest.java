package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 12:24
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_register_user() throws Exception {
        User user =new User("Bob", "male", 18,"a@b.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Bob")))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[0].age",is(18)))
                .andExpect(jsonPath("$[0].email",is("a@b.com")))
                .andExpect(jsonPath("$[0].phone",is("12345678911")))
                .andExpect(status().isOk());
    }

    @Test
    void should_error_when_name_too_long() throws Exception {
        User user =new User("Bobbbbbbbbbbbbbbb", "male", 18,"a@b.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void should_error_when_gender_is_null() throws Exception {
        User user =new User("Bob", null, 18,"a@b.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void should_error_when_age_out_of_range() throws Exception {
        User user =new User("Bob", "male", 1,"a@b.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void should_error_when_email_irregular() throws Exception {
        User user =new User("Bob", "male", 18,"abb.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void should_error_when_phone_irregular() throws Exception {
        User user = new User("Bob", "male", 18,"a@b.com","123456789111111");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void should_return_false_when_userName_is_not_exist(){
        UserController userController = new UserController();
        User user = new User("Tom", "male", 18,"a@b.com","123456789111111");
        Boolean flag = userController.isUserNameExist(user);
        assertEquals(false,flag);
    }
    @Test
    void should_return_true_when_userName_is_not_exist(){
        UserController userController = new UserController();
        User user = new User("Bob", "male", 18,"a@b.com","123456789111111");
        Boolean flag = userController.isUserNameExist(user);
        assertEquals(true,flag);
    }

    @Test
    void should_return_all_user_list() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("Bob")))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[0].age",is(18)))
                .andExpect(jsonPath("$[0].email",is("a@b.com")))
                .andExpect(jsonPath("$[0].phone",is("12345678911")))
                .andExpect(status().isOk());

    }
}
