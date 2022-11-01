package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Transaction;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.TransactionRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction newTransactionService (TransactionDto transactionDto) throws InputMismatchException {
        // Transaction de montant non nul et de solde suffisant
        // Attention - ne crée que la transaction - on fait ensuite un addTransactionToUser de UserService (voir TransactionTestIT
        if (transactionDto.getAmount() > 0.0) {
            if ( transactionDto.getAmount() > transactionDto.getUserFrom().getBalance()) {
                throw new InputMismatchException("Account balance (" + transactionDto.getUserFrom().getBalance() + ") insufficient for debit (" + transactionDto.getAmount() + ")");
            } if (transactionDto.getUserFrom().getConnections().contains(transactionDto.getUserTo())) {
                Transaction transaction = new Transaction();
                transaction.setDescription(transactionDto.getDescription());
                transaction.setDateTransaction(LocalDateTime.now());
                transaction.setAmount(transactionDto.getAmount());
                transaction.setCommission(Math.ceil(transactionDto.getAmount()*0.5)/100);
                transaction.setUserFrom(transactionDto.getUserFrom());
                transaction.setUserTo(transactionDto.getUserTo());
                return transactionRepository.save(transaction);
            }
            // Si le user de destination n'est pas dans les contacts du user d'origine
            else {
                throw new InputMismatchException("User " + transactionDto.getUserTo().getFirstName() + ", " + transactionDto.getUserTo().getLastName() +
                        " is not a connection of " + transactionDto.getUserFrom().getFirstName() + ", " + transactionDto.getUserFrom().getLastName());
            }
        } else {
            throw new InputMismatchException("Transaction with no account");
        }
    }

}
