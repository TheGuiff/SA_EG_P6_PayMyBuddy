package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    LogRepository logRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board")
    public String board(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",log);
        return "board";
    }

}
