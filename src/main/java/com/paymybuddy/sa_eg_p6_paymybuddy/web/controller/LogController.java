package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.LogService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/createUser")
    public String createUser(Model model) {
        NewUserDto newUserDto = new NewUserDto();
        model.addAttribute("newUserDto", newUserDto);
        return "saveUser";
    }

    @PostMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute NewUserDto newUserDto) {
        logService.newUserAndLog(newUserDto);
        return new ModelAndView("redirect:/");
    }
}
