package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.service.LogService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.InputMismatchException;

@Controller
@Slf4j
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/createUser")
    public String createUser(Model model) {
        log.info("Create new user");
        NewUserDto newUserDto = new NewUserDto();
        model.addAttribute("newUserDto", newUserDto);
        return "saveUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute NewUserDto newUserDto, Model model) {
        log.info("Save new user");
        try {
            logService.newUserAndLog(newUserDto);
            return "/";
        } catch (InputMismatchException e) {
            log.error("Error:{}", e.getMessage());
            model.addAttribute("Error",e.getMessage());
            return "saveUser";
        }
    }

}
