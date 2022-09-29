package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.*;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    @Transactional
    public User addConnection (User user, String email) {
        List<Log> logConnection = logRepository.findByEmail(email);
        if (logConnection.size() == 1) {
            user.getConnections().add(logConnection.get(0).getUser());
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException("unknown email address");
        }
    }

    @Transactional
    public User addMovementToUser (User user, Movement movement) {
        if (movement.getType() == TypeMovement.CREDIT) {
            user.setBalance(user.getBalance() + movement.getAmount());
        } else {
            user.setBalance(user.getBalance() - movement.getAmount());
        }
        user.getMovements().add(movement);
        return userRepository.save(user);
    }

    @Transactional
    public User addTransactionToUser (User userFrom, User userTo, Transaction transaction) {
        userFrom.setBalance(userFrom.getBalance()-transaction.getAmount());
        userTo.setBalance(userTo.getBalance()+transaction.getAmount()-transaction.getCommission());
        userFrom.getTransactions().add(transaction);
        userRepository.save(userTo);
        return userRepository.save(userFrom);
    }

}