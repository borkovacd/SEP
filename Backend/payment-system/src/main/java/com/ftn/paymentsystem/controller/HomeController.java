package com.ftn.paymentsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello() {
        return "Welcome to spring boot application";
    }

}