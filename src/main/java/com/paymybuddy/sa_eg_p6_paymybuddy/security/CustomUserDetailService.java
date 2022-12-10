package com.paymybuddy.sa_eg_p6_paymybuddy.security;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    LogRepository logRepository;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        final List<Log> listLog = logRepository.findByEmail(email);
        if (listLog.size() == 0) {
            throw new UsernameNotFoundException(email);
        }
        return new MyLogPrincipal(listLog.get(0));
    }

}
