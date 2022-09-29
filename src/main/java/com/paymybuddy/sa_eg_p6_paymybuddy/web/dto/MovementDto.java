package com.paymybuddy.sa_eg_p6_paymybuddy.web.dto;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.TypeMovement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementDto {

    private TypeMovement type;
    private Double amount;
    private User user;

}
