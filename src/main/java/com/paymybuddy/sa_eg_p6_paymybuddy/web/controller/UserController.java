package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.TypeMovement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.UserService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.ConnectionDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board")
    public String board(HttpServletRequest request, Model model) {
        //User connecté
        Principal principal = request.getUserPrincipal();
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",log);
        //Nouvelle transaction
        TransactionWebDto transactionWebDto = new TransactionWebDto();
        model.addAttribute("transactionWebDto", transactionWebDto);
        return "board";
    }

    @GetMapping("/connection")
    public String connection(Model model) {
        //Nouvelle connexion
        ConnectionDto connectionDto = new ConnectionDto();
        model.addAttribute("connectionDto", connectionDto);
        return "connection";
    }

    @PostMapping("/newConnection")
    public ModelAndView newConnection(HttpServletRequest request,
                                    @ModelAttribute ConnectionDto connectionDto,
                                    Model model) throws Exception {
        Principal principal = request.getUserPrincipal();
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        connectionDto.setUser(log.getUser());
        userService.addConnection(connectionDto);
        return new ModelAndView("redirect:/board");
    }

}
