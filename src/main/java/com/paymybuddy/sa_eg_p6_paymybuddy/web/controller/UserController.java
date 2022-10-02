package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board")
    public String board() { return "board"; }

    @GetMapping("/createUser")
    public String createUser() {return "createUser"; }

}
