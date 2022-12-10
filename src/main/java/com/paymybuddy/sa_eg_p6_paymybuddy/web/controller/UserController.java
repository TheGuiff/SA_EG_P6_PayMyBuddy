package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.UserService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.ConnectionDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.InputMismatchException;

@Controller
@Slf4j
public class UserController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        log.info("Connection to the app");
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        //User connecté
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",myLog);
        log.info("Go to board for : " + myLog.getEmail());
        return "home";
    }

    @GetMapping("/transfert")
    public String transfert(HttpServletRequest request, Model model) {
        //User connecté
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",myLog);
        log.info("Go to transfert for : " + myLog.getEmail());
        //Nouvelle transaction
        TransactionWebDto transactionWebDto = new TransactionWebDto();
        model.addAttribute("transactionWebDto", transactionWebDto);
        return "transfert";
    }

    @GetMapping("/connection")
    public String connection(Model model) {
        //Nouvelle connexion
        log.info("New connection");
        ConnectionDto connectionDto = new ConnectionDto();
        model.addAttribute("connectionDto", connectionDto);
        return "connection";
    }

    @PostMapping("/newConnection")
    public String newConnection(HttpServletRequest request,
                                    @ModelAttribute ConnectionDto connectionDto,
                                    Model model) throws Exception {
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        connectionDto.setUser(myLog.getUser());
        log.info("Save new connection between " + myLog.getEmail() + " and " + connectionDto.getEmailConnection() );
        try {
            userService.addConnection(connectionDto);
        } catch (InputMismatchException e) {
            log.error("Error : {}", e.getMessage());
            model.addAttribute("Error", e.getMessage());
            return "connection";
        }
        return "home";
    }

}
