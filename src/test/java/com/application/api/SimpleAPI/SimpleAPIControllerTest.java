package com.application.api.SimpleAPI;

import com.application.api.SimpleAPI.controller.SimpleAPIController;
import com.application.api.SimpleAPI.model.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SimpleAPIControllerTest {

    @Mock
    Environment environment;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private SimpleAPIController controller;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_saveGameDetailsPostRequest_ShouldReturnSuccess() throws Exception {
        when(environment.getProperty("round.latency")).thenReturn("0");
        Game game = new Game("test", "testid", 10L);
        // given(controller.saveGameDetails(game)).willReturn(ResponseEntity.ok(game));
        controller.saveGameDetails(game);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(game)))
                .andExpect(jsonPath("game").value("test"))
                .andExpect(status().isOk());


    }

    @Test
    public void test_saveGameDetailsPostRequest_WithWrongRequest_body_ShouldReturnBad() throws Exception {
        Game game = new Game("test", null, 10L);
        when(environment.getProperty("round.latency")).thenReturn("0");
        controller.saveGameDetails(game);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(game)))
                //   .andExpect(jsonPath("game").value("test"))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void test_saveGameDetailsPostRequest_shouldReturnBadRequest() throws Exception {
        Game game = new Game("test", null, 10L);
        when(environment.getProperty("round.latency")).thenReturn("0");
        controller.saveGameDetails(game);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                        .content(objectMapper.writeValueAsBytes(game))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_PingRequest_ShouldReturnSuccess() throws Exception {
        when(environment.getProperty("round.latency")).thenReturn("0");
        controller.getHeartBeat();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ping")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void test_saveDetails_WithLatency_ShouldReturnSuccess() {
        Game game = new Game("test", "testid", 10L);
        when(environment.getProperty("round.latency")).thenReturn("500");
        ResponseEntity<Game> actual = controller.saveGameDetails(game);
        Assertions.assertEquals(Objects.requireNonNull(actual.getBody()).game(), game.game());

    }
}
