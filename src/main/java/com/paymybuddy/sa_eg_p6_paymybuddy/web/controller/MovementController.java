package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.MovementInternalService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
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
public class MovementController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    MovementInternalService movementInternalService;

    @GetMapping("/movement")
    public String movement(HttpServletRequest request,
                        Model model) {
        //User connecté
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",myLog);
        log.info("new movement for : "+ myLog.getEmail());
        //Nouveau mouvement
        MovementDto movementDto = new MovementDto();
        model.addAttribute("movementDto", movementDto);
        return "movement";
    }

    @PostMapping("/newMovement")
    public String newMovement(HttpServletRequest request,
                                 @ModelAttribute MovementDto movementDto,
                                    Model model) {
        log.info("Save new movement");
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        movementDto.setUser(myLog.getUser());
        model.addAttribute("log",myLog);
        try {
            movementInternalService.newMovementInternalService(movementDto);
        } catch (InputMismatchException e) {
            log.error("Error:{}", e.getMessage());
            model.addAttribute("Error",e.getMessage());
            return "movement";
        }
        return "home";
    }
}
