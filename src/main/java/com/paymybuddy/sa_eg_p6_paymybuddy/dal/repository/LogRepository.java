package com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByEmail (String email);

    default String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    default Log hashPasswordAndSave(Log log) {
        log.setMdp(hashPassword(log.getMdp()));
        return save(log);
    }

}
