package com.example.demo.Controller;

import com.example.demo.Converter.UserConverter;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import com.example.demo.model.PetDto;
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
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private  UserService userService;
    @Autowired
    private PetService petService;
    @Autowired
    private  UserConverter userConverter;

    @Test
    void createPet() throws Exception {
        var user = new UserDto(
                null,
                "Ivan",
                "vanyushagtr270@mail.ru",
                20,
                new ArrayList<>()
        );

        var createdUser = userService.createUser(userConverter.toUser(user));

        var pet = new PetDto(
                   null,
                createdUser.id(),
                "Tychka"
        );

        String petJson = objectMapper.writeValueAsString(pet);

        String createdPetJson = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = objectMapper.readValue(createdPetJson, PetDto.class);

        org.junit.jupiter.api.Assertions.assertEquals(pet.name(), petResponse.name());
        org.junit.jupiter.api.Assertions.assertEquals(pet.userId(), petResponse.userId());

        Assertions.assertDoesNotThrow(() -> petService.findPetById(petResponse.id()));

    }
}