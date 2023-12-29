package com.application.api.SimpleAPI;

import com.application.api.SimpleAPI.controller.SimpleAPIController;
import com.application.api.SimpleAPI.model.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SimpleAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SimpleAPIController controller;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_saveGameDetailsPostRequest_ShouldReturnSuccess() throws Exception {
        Game game = new Game("test", "testid", 10L);
        given(controller.saveGameDetails(game)).willReturn(ResponseEntity.ok(game));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(game)))
                .andExpect(jsonPath("game").value("test"))
                .andExpect(status().isOk());


    }

    @Test
    public void test_saveGameDetailsPostRequest_WithWrongRequest_body_ShouldReturnBad() throws Exception {
        Game game = new Game("test", null, 10L);
        given(controller.saveGameDetails(game)).willReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(game)))
                //   .andExpect(jsonPath("game").value("test"))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void test_saveGameDetailsPostRequest_shouldReturnBadRequest() throws Exception {
        Game game = new Game("test", "testid", 10L);

        given(controller.saveGameDetails(game)).willReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .content(objectMapper.writeValueAsBytes(game))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_PingRequest_ShouldReturnSuccess() throws Exception {

        given(controller.getHeartBeat()).willReturn(ResponseEntity.ok("Pong!"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ping")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void test_PingRequest_shouldReturnBadRequest() throws Exception {


        given(controller.getHeartBeat()).willReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ping")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
