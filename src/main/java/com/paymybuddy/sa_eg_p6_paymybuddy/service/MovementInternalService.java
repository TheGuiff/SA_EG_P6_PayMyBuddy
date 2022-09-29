package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Movement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.User;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class MovementInternalService {

    @Autowired
    MovementService movementService;

    @Autowired
    UserService userService;

    @Transactional
    public Movement newMovementInternalService (MovementDto movementDto) throws Exception {
        Movement movement = movementService.newMovementService(movementDto);
        User user = userService.addMovementToUser(movementDto.getUser(), movement);
        movement.setUser(user);
        return movement;
    }

}
