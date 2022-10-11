package com.paymybuddy.sa_eg_p6_paymybuddy.security;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class MyLogPrincipal implements UserDetails {

    private final Log log;

    public MyLogPrincipal(Log log) {
        this.log = log;
    }

    @Override
    public String getUsername() {
        return log.getEmail();
    }

    @Override
    public String getPassword() {
        return log.getMdp();
    }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
