package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.TypeMovement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.MovementInternalService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView newMovement(HttpServletRequest request,
                                 @ModelAttribute MovementDto movementDto,
                                    Model model) {
        log.info("Save new movement");
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        movementDto.setUser(myLog.getUser());
        try {
            movementInternalService.newMovementInternalService(movementDto);
        } catch (InputMismatchException e) {
            log.error("Error:{}", e.getMessage());
            model.addAttribute("Error",e.getMessage());
            Principal p = request.getUserPrincipal();
            Log myLogE = logRepository.findByEmail(p.getName()).get(0);
            model.addAttribute("log",myLogE);
            return new ModelAndView("/movement");
        }
        return new ModelAndView("redirect:/board");
    }
}
