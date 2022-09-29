package com.paymybuddy.sa_eg_p6_paymybuddy.web.dto;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private String description;
    private Double amount;
    private User userFrom;
    private User userTo;

}
