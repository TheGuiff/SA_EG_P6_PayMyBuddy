package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.TransactionInternalService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.InputMismatchException;

@Controller
@Slf4j
public class TransactionController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    TransactionInternalService transactionInternalService;

    @PostMapping("/newTransaction")
    public String saveUser(HttpServletRequest request,
                           @ModelAttribute TransactionWebDto transactionWebDto,
                           Model model) {
        Principal principal = request.getUserPrincipal();
        Log myLog = logRepository.findByEmail(principal.getName()).get(0);
        log.info("New transaction from " + myLog.getEmail() + " to : " + transactionWebDto.getMailTo());
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription(transactionWebDto.getDescription());
        transactionDto.setAmount(transactionWebDto.getAmount());
        transactionDto.setUserFrom(myLog.getUser());
        try {
            transactionDto.setUserTo(logRepository.findByEmail(transactionWebDto.getMailTo()).get(0).getUser());
        } catch (Exception e) {
            log.error("Error : unknown email address");
            model.addAttribute("Error", e.getMessage());
            Principal p = request.getUserPrincipal();
            Log myLogE = logRepository.findByEmail(p.getName()).get(0);
            model.addAttribute("log",myLogE);
            model.addAttribute("listC", myLogE.getUser().getConnections());
            return "transfert";
        }
        try {
            transactionInternalService.newTransactionInternalService(transactionDto);
            Principal p = request.getUserPrincipal();
            Log myLogE = logRepository.findByEmail(p.getName()).get(0);
            model.addAttribute("log",myLogE);
            model.addAttribute("listC", myLogE.getUser().getConnections());
            //return "home";
            return"transfert";
        } catch (InputMismatchException e) {
            log.error("Error : {}",e.getMessage());
            model.addAttribute("Error", e.getMessage());
            Principal p = request.getUserPrincipal();
            Log myLogE = logRepository.findByEmail(p.getName()).get(0);
            model.addAttribute("log",myLogE);
            model.addAttribute("listC", myLogE.getUser().getConnections());
            return "transfert";
        }
    }
}
