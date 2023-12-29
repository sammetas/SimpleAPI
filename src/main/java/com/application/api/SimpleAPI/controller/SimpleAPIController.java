package com.application.api.SimpleAPI.controller;

import com.application.api.SimpleAPI.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class SimpleAPIController {

    @Autowired
    Environment environment;

    @GetMapping("/ping")
    public ResponseEntity<String> getHeartBeat() {
        long latency = Long.parseLong(Objects.requireNonNull(environment.getProperty("round.latency")));
        if (latency != 0L) {
            try {
                Thread.sleep(latency); //Added sleep logic to simulate the latency /slow response
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok("Pong!");
    }

    @PostMapping("/game")
    public ResponseEntity<Game> saveGameDetails(@RequestBody Game game) {
        long latency = Long.parseLong(Objects.requireNonNull(environment.getProperty("round.latency")));
        if (isValid(game)) {
            if (latency != 0L) {
                try {
                    Thread.sleep(latency); //Added sleep logic to simulate the latency /slow response
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("Game Object Saved using port:" + environment.getProperty("local.server.port"));
            return ResponseEntity.ok(game);
        }

        return ResponseEntity.badRequest().build();
    }

    /*
       Method to validate game details. Presumed validations for both game and gamer Id
     */
    private boolean isValid(Game game) {
        return Objects.nonNull(game.game()) && Objects.nonNull(game.gamerID());
    }

}
