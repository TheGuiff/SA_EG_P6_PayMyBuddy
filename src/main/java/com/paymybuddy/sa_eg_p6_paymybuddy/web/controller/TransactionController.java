package com.paymybuddy.sa_eg_p6_paymybuddy.web.controller;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.TransactionInternalService;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.TransactionService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class TransactionController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    TransactionInternalService transactionInternalService;

    @PostMapping("/newTransaction")
    public ModelAndView saveUser(HttpServletRequest request,
                                 @ModelAttribute TransactionWebDto transactionWebDto) throws Exception {
        Principal principal = request.getUserPrincipal();
        Log log = logRepository.findByEmail(principal.getName()).get(0);
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription(transactionWebDto.getDescription());
        transactionDto.setAmount(transactionWebDto.getAmount());
        transactionDto.setUserFrom(log.getUser());
        transactionDto.setUserTo(logRepository.findByEmail(transactionWebDto.getMailTo()).get(0).getUser());
        transactionInternalService.newTransactionInternalService(transactionDto);
        return new ModelAndView("redirect:/board");
    }
}
