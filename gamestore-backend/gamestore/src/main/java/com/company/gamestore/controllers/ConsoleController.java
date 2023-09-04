package com.company.gamestore.controllers;


import com.company.gamestore.models.Console;
import com.company.gamestore.repositories.ConsoleRepo;
import com.company.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ConsoleController {

    @Autowired
    ServiceLayer serviceLayer;
//    Create mapping

    @PostMapping("/consoles")
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody @Valid Console toAdd){return serviceLayer.addConsole(toAdd);}

//    Read and Read all mappings
    @GetMapping("/consoles")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsoles(){return serviceLayer.findAllConsoles();}

    //    Read by id
    @GetMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsolesById(@PathVariable int id){
        return serviceLayer.findConsoleById(id);

    }
    @PutMapping("/consoles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody Console console){
        serviceLayer.updateConsole(console);
    }

    @DeleteMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id){
        serviceLayer.removeConsole(id);
    }

//    findByManufacturer
    @GetMapping("/consoles/manufacturer/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> findByManufacturer(@PathVariable String name){
        return serviceLayer.findConsoleByManufacturer(name);
    }
}
