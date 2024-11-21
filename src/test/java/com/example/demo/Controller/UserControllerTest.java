package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import com.example.demo.model.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser() throws Exception {
        var user = new UserDto(
                null,
                "Ivan",
                "vanyushagtr270@mail.ru",
                20,
                new ArrayList<>()
        );

        String userJson = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        )
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);

        org.junit.jupiter.api.Assertions.assertEquals(user.name(), userResponse.name());
        org.junit.jupiter.api.Assertions.assertEquals(user.age(), userResponse.age());
        org.junit.jupiter.api.Assertions.assertEquals(user.email(), userResponse.email());
        org.junit.jupiter.api.Assertions.assertEquals(user.pets(), userResponse.pets());


        Assertions.assertDoesNotThrow(() -> userService.getUserById(userResponse.id()));


    }

}