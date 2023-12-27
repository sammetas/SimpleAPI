package com.application.api.SimpleAPI.controller;

import com.application.api.SimpleAPI.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class SimpleAPIController {

    @GetMapping("/ping")
    public ResponseEntity<String> getBeat(){
        return ResponseEntity.ok("Pong!");
    }

    @PostMapping("/game")
    public ResponseEntity<Game> saveGameDetails(@RequestBody Game game) {
        if (isValid(game)) {
            log.info("Game Object Saved");
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
