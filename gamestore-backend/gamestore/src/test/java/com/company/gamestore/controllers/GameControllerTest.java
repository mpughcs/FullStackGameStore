package com.company.gamestore.controllers;

import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.GameRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private GameRepo repo;
    Game game = new Game("Test Game", "E", "Description", new BigDecimal("49.99"), "Studio", 10);
    @BeforeEach
    public void setUp() throws Exception {
        repo.deleteAll();
        inputJson = mapper.writeValueAsString(game);
    }

    String inputJson;

    @Test
    void shouldAddGame() throws Exception {
        mockMvc.perform(
                        post("/games")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetGames() throws Exception {
        repo.save(game); // Save a game before performing the test
        mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetGameById() throws Exception {
        repo.save(game);
        mockMvc.perform(get("/games/{id}", game.getGame_id()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateGame() throws Exception {
        repo.save(game);
        mockMvc.perform(
                        put("/games/", game)
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteGame() throws Exception {
        repo.save(game);
        mockMvc.perform(delete("/games/{id}", game.getGame_id()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}