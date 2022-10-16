package com.paymybuddy.sa_eg_p6_paymybuddy.dummy;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.MovementRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.TransactionRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Me permet de faire mon @AfterAll
public class ProdExamplesTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MovementRepository movementRepository;


    private static final String email1 = "user1@test.com";
    private static final String mdp = "1234";
    private static final String firstName1 = "Alain";
    private static final String email2 = "user2@test.com";
    private static final String firstName2 = "Alex";
    private static final String lastName = "Térieur";
    private static final Double balance = 100.0;

    @BeforeAll
    public void initDataBase(){
        //Reinit de la base
        transactionRepository.deleteAll();
        movementRepository.deleteAll();
        logRepository.deleteAll();
        userRepository.deleteAll();

        //User 1 - log et user
        Log log1 = new Log();
        log1.setEmail(email1);
        log1.setMdp(mdp);
        User user1 = new User();
        user1.setFirstName(firstName1);
        user1.setLastName(lastName);
        user1.setBalance(balance);
        user1 = userRepository.save(user1);
        log1.setUser(user1);
        logRepository.hashPasswordAndSave(log1);

        //User 2 - log et user
        Log log2 = new Log();
        log2.setEmail(email2);
        log2.setMdp(mdp);
        User user2 = new User();
        user2.setFirstName(firstName2);
        user2.setLastName(lastName);
        user2.setBalance(balance);
        user2 = userRepository.save(user2);
        log2.setUser(user2);
        logRepository.hashPasswordAndSave(log2);

        //user2 est une connection de user1
        user1.getConnections().add(user2);
        user2.getConnections().add(user1);
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

    }

    @Test
    public void dumTest() {
        assertEquals(1,1);
    }

}
