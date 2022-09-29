package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Transaction;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionInternalService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @Transactional
    public Transaction newTransactionInternalService (TransactionDto transactionDto) throws Exception {
        Transaction transaction = transactionService.newTransactionService(transactionDto);
        userService.addTransactionToUser(transactionDto.getUserFrom(), transactionDto.getUserTo(), transaction);
        return transaction;
    }

}
