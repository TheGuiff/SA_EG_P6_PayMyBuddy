package com.paymybuddy.sa_eg_p6_paymybuddy.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionWebDto {

    private String description;
    private Double amount;
    private String mailTo;

}
