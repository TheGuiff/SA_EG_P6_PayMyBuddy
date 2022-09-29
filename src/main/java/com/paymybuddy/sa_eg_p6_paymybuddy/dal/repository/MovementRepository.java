package com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
}
