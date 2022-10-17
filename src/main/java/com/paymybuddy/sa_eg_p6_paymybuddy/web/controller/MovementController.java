package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.TypeMovement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.MovementInternalService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
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
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        model.addAttribute("log",log);
        //Nouveau mouvement
        MovementDto movementDto = new MovementDto();
        model.addAttribute("movementDto", movementDto);
        return "movement";
    }

    @PostMapping("/newMovement")
    public ModelAndView newMovement(HttpServletRequest request,
                                 @ModelAttribute MovementDto movementDto,
                                    Model model) throws Exception {
        Principal principal = request.getUserPrincipal();
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        movementDto.setUser(log.getUser());
        movementInternalService.newMovementInternalService(movementDto);
        return new ModelAndView("redirect:/board");
    }
}
