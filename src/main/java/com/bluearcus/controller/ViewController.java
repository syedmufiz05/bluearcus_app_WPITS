package com.bluearcus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showMsg() {
        System.out.println("Hello updated server on 15/12/2023");
        return "Hello updated server on 15/12/2023";
    }
}
