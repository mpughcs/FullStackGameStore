package com.company.gamestore.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping(value = "/")
    public String index() {
        return "Game Store API Home Page";
    }
}
