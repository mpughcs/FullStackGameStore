package com.company.gamestore.controllers;

import com.company.gamestore.models.Console;
import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.ConsoleRepo;
import com.company.gamestore.repositories.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GraphController {
    @Autowired
    ConsoleRepo consoleRepo;

    @Autowired
    GameRepo gameRepo;

    //console queries
    @QueryMapping
    public List<Console> getConsoles(){
        return consoleRepo.findAll();
    }
    @QueryMapping
    public List<Console> getConsoleByManufacturer(@Argument String manufacturer){
        return consoleRepo.findByManufacturer(manufacturer);
    }
    @QueryMapping
    public Console getConsoleByID(@Argument(name = "id") int id) {
        return consoleRepo.findById(id).orElse(null);
    }

//    Game Queries

    @QueryMapping
    public List<Game> getGames(){
        return gameRepo.findAll();
    }
    @QueryMapping
    public Game getGameByID(@Argument(name = "game_id") int game_id) {
        return gameRepo.findById(game_id).orElse(null);
    }
    @QueryMapping
    public List<Game> getGameByTitle(@Argument String title){
        return gameRepo.findByTitle(title);
    }

    @QueryMapping
    public List<Game> getGameByRating(@Argument String rating){
        return gameRepo.findByRating(rating);
    }

    @QueryMapping
    public List<Game> getGameByStudio(@Argument String studio){
        return gameRepo.findByStudio(studio);
    }



//
}
