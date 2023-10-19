package com.company.gamestore.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTML;

@RestController
public class DefaultController {
    @RequestMapping(value = "/")
    public String index() {

        return "Game Store API Home Page";
    }
}
