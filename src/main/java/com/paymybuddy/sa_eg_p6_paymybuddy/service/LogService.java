package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.UserRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.LogDto;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    public Log newUserAndLog (NewUserDto newUserDto) throws InputMismatchException {
        // Nouvel utilisateur - vérifie qu'il n'existe pas déjà et qu'un mdp est renseigné
        List<Log> logConnections = logRepository.findByEmail(newUserDto.getEmail());
        if (logConnections.size() == 1) { //Utilisateur existe déjà
            throw new InputMismatchException("email already exist in database");
        } else if (newUserDto.getMdp().length() == 0) {
            throw new InputMismatchException("password can't be empty");
        } else {
            Log log = new Log();
            User user = new User();
            log.setEmail(newUserDto.getEmail());
            log.setMdp(newUserDto.getMdp());
            user.setFirstName(newUserDto.getFirstName());
            user.setLastName(newUserDto.getLastName());
            user.setBalance(0.0);
            user = userRepository.save(user);
            log.setUser(user);
            return logRepository.hashPasswordAndSave(log);
        }
    }

}
