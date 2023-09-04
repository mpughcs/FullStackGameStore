package com.company.gamestore.repositories;

import com.company.gamestore.models.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConsoleRepoTest {

    @Autowired
    private ConsoleRepo repo;

    Console testConsole = new Console("XboxOne","Microsoft","16gb","I7",new BigDecimal("499.99"),100);

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void shouldAddConsole(){
        testConsole=repo.save(testConsole);
        Optional<Console> expected = repo.findById(testConsole.getId());
        assertEquals(expected.get(),testConsole);
    }

    @Test
    void shouldFindConsoles(){
        testConsole=repo.save(testConsole);
        List<Console> expected = new ArrayList<>();
        expected.add(testConsole);
        assertEquals(repo.findAll(),expected);
    }

    @Test
    void shouldFindConsoleById(){
        testConsole=repo.save(testConsole);
        Optional<Console> expected = repo.findById(testConsole.getId());
        assertEquals(expected.get(),testConsole);
    }

    @Test
    void shouldUpdateConsole(){
        testConsole=repo.save(testConsole);
        Console updated= new Console("XboxOn","Microsoft","16gb","I7",new BigDecimal("499.99"),100);
        updated.setId(testConsole.getId());
        repo.save(updated);
        assertEquals(updated, repo.findById(testConsole.getId()).get());
    }

    @Test
    void shouldDeleteConsole(){
        testConsole=repo.save(testConsole);
        Optional<Console> optionalConsole;
        repo.delete(testConsole);

        optionalConsole=repo.findById(testConsole.getId());
        assertFalse(optionalConsole.isPresent(), "console should be deleted");

    }

    @Test
    void shouldGetConsoleByManufacturer(){
        testConsole=repo.save(testConsole);
        List<Console> microsoftConsoles=new ArrayList<>();
        microsoftConsoles.add(testConsole);
        assertEquals(microsoftConsoles, repo.findByManufacturer("Microsoft"));
    }


}