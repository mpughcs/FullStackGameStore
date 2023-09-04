package com.company.gamestore.repositories;

import com.company.gamestore.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GameRepoTest {

    @Autowired
    private GameRepo gameRepo;

    @BeforeEach
    void setUp() {
        gameRepo.deleteAll();
    }


    @Test
    public void testSaveGame() throws Exception{
        Game game = new Game("Test Game", "E", "Description", new BigDecimal("49.99"), "Studio", 10);
        gameRepo.save(game);

        assertThat(game.getGame_id()).isNotNull();
    }

    @Test
    public void testFindById() {
        Game game = new Game("Test Game", "E", "Description", new BigDecimal("49.99"), "Studio", 10);
        game = gameRepo.save(game);

        Game retrievedGame = gameRepo.findById(game.getGame_id()).orElse(null);
        assertThat(retrievedGame).isNotNull();
        assertThat(retrievedGame.getTitle()).isEqualTo("Test Game");
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game("Test Game", "E", "Description", new BigDecimal("49.99"), "Studio", 10);
        game = gameRepo.save(game);

        game.setTitle("Updated Title");
        gameRepo.save(game);

        Game updatedGame = gameRepo.findById(game.getGame_id()).orElse(null);
        assertThat(updatedGame).isNotNull();
        assertThat(updatedGame.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeleteGame() {
        Game game = new Game("Test Game", "E", "Description", new BigDecimal("49.99"), "Studio", 10);
        game = gameRepo.save(game);

        gameRepo.deleteById(game.getGame_id());

        assertThat(gameRepo.findById(game.getGame_id())).isEmpty();
    }

    @Test
    public void testFindAllGames() {
        gameRepo.save(new Game("Game 1", "E", "Desc 1", new BigDecimal("49.99"), "Studio 1", 10));
        gameRepo.save(new Game("Game 2", "T", "Desc 2", new BigDecimal("39.99"), "Studio 2", 5));

        List<Game> games = gameRepo.findAll();
        assertThat(games).hasSize(2);
    }
}
