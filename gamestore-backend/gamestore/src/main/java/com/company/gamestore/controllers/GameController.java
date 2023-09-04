package com.company.gamestore.controllers;

import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.GameRepo;
import com.company.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/games")
public class GameController {



    @Autowired
    private ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return serviceLayer.getGames();
    }

    @GetMapping("/{game_id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGameById(@PathVariable int game_id) {
        return serviceLayer.getGameById(game_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return serviceLayer.addGame(game);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody Game game) {
        serviceLayer.updateGame(game);
    }

    @DeleteMapping("/{game_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int game_id) {
        serviceLayer.deleteGame(game_id);
    }

    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGameByTitle(@PathVariable String title) {
        return serviceLayer.getGameByTitle(title);
    }

    @GetMapping("/rating/{rating}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGameByRating(@PathVariable String rating) {
        return serviceLayer.getGameByEsrbRating(rating);
    }

    @GetMapping("/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGameByStudio(@PathVariable String studio) {
        return serviceLayer.getGameByStudio(studio);
    }
}
