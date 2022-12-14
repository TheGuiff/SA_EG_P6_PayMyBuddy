package com.paymybuddy.sa_eg_p6_paymybuddy;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.MovementRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.TransactionRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.UserRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.LogService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.LogDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test") // Utilisation du application-test.properties pour les tests à la place du application.properties (BDD de test)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Me permet de faire mon @AfterAll
public class LogServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MovementRepository movementRepository;
    @Autowired
    LogService logService;

    private static final String email1 = "email1@test.com";
    private static final String mdp1 = "mdpTest1";
    private static final String firstName1 = "Firstname1";
    private static final String lastName1 = "Lastname1";

    private static final String email2 = "email2@test.com";
    private static final String mdp2 = "mdpTest2";
    private static final String firstname2 = "Firstname2";
    private static final String lastname2 = "Lastname2";

    private final LogDto logDto = new LogDto();
    private static final String emailKo = "emailKO@test.com";
    private static final String mdpKo = "mdpKoTest";
    private final NewUserDto newUserDto = new NewUserDto();

    @BeforeEach
    public void initDataBase() {
        //Reinit de la base
        transactionRepository.deleteAll();
        movementRepository.deleteAll();
        logRepository.deleteAll();
        userRepository.deleteAll();

        //User 1 - log et user
        Log log1 = new Log();
        log1.setEmail(email1);
        log1.setMdp(mdp1);
        User user1 = new User();
        user1.setFirstName(firstName1);
        user1.setLastName(lastName1);
        user1.getMovements().clear();
        user1.getTransactions().clear();
        user1.getConnections().clear();
        user1 = userRepository.save(user1);
        log1.setUser(user1);
        logRepository.hashPasswordAndSave(log1);
    }

    @AfterAll
    public void emptyBase() {
        transactionRepository.deleteAll();
        movementRepository.deleteAll();
        logRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void newUserOk() {
        newUserDto.setEmail(email2);
        newUserDto.setLastName(lastname2);
        newUserDto.setFirstName(firstname2);
        newUserDto.setMdp(mdp2);
        Log logOut = logService.newUserAndLog(newUserDto);
        assertEquals(email2, logOut.getEmail());
        assertEquals(firstname2, logOut.getUser().getFirstName());
        assertEquals(lastname2, logOut.getUser().getLastName());
    }

    @Test
    public void newUserKoPasswordEmpty() {
        newUserDto.setEmail(email2);
        newUserDto.setLastName(lastname2);
        newUserDto.setFirstName(firstname2);
        newUserDto.setMdp("");
        Assertions.assertThrows(InputMismatchException.class,() -> logService.newUserAndLog(newUserDto));
    }

    @Test
    public void newUserKoEmailAlreadyExist() {
        newUserDto.setEmail(email1);
        newUserDto.setLastName(lastname2);
        newUserDto.setFirstName(firstname2);
        newUserDto.setMdp("");
        Assertions.assertThrows(InputMismatchException.class,() -> logService.newUserAndLog(newUserDto));
    }
}
