package com.paymybuddy.sa_eg_p6_paymybuddy.web.dto;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectionDto {

    public User user;
    public String emailConnection;

}
