package com.paymybuddy.sa_eg_p6_paymybuddy.service;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Movement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.TypeMovement;
import com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository.MovementRepository;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.MovementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Transactional
    public Movement newMovementService (MovementDto movementDto) throws InputMismatchException {
        //vérifie mouvement supérieur à zéro et inférieur au solde (si retrait)
        // Attention - ne crée que le mouvement - on fait ensuite un addMovementToUser de UserService - voir MovementTestIT
        if (movementDto.getAmount() > 0.0) {
            if (movementDto.getType() == TypeMovement.DEBIT && movementDto.getAmount() > movementDto.getUser().getBalance()) {
                throw new InputMismatchException("Account balance (" + movementDto.getUser().getBalance() + ") insufficient for debit (" + movementDto.getAmount() + ")");
            } else {
                Movement movement = new Movement();
                movement.setType(movementDto.getType());
                movement.setAmount(movementDto.getAmount());
                movement.setDateMovement(LocalDateTime.now());
                movement.setUser(movementDto.getUser());
                return movementRepository.save(movement);
            }
        } else {
            throw new InputMismatchException("Movement with no account");
        }
    }

}
