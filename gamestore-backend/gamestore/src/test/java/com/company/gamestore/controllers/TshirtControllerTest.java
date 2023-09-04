package com.company.gamestore.controllers;

import com.company.gamestore.models.Tshirt;
import com.company.gamestore.repositories.TshirtRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TshirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TshirtRepo repo;

    Tshirt t = new Tshirt("medium","red","pretty", new BigDecimal("499.99"),100);

    String inputJson;

    @BeforeEach
    void setUp() throws Exception{
        repo.deleteAll();
        inputJson = mapper.writeValueAsString(t);
        //repo.save(t);

    }

    //Tests

    @Test
    void shouldAddTshirt() throws Exception{
        t = repo.save(t);
        inputJson = mapper.writeValueAsString(t);

        mockMvc.perform(
                post("/tshirts")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetTshirtBytId() throws Exception{
        mockMvc.perform(get("/tshirts/{id}", t.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTshirts() throws Exception{
        mockMvc.perform(get("/tshirts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTshirt() throws Exception{
        repo.save(t);
        Tshirt t2 = new Tshirt("large","blue","pretty", new BigDecimal("499.99"),100);
        String inputJson2 = mapper.writeValueAsString(t2);
        mockMvc.perform(put("/tshirts")
                .content(inputJson2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteTshirt() throws Exception{
        repo.save(t);
        mockMvc.perform(delete("/tshirts/{id}", t.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTshirtByColor() throws Exception{
        repo.save(t);
        mockMvc.perform(get("/tshirts/color/{color}", t.getColor()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTshirtBySize() throws Exception{
        repo.save(t);
        mockMvc.perform(get("/consoles/manufacturer/{name}",t.getSize()))
                .andDo(print())
                .andExpect(status().isOk());

    }
}