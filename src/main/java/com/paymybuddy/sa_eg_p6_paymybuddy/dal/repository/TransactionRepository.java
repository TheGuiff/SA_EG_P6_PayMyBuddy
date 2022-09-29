package com.paymybuddy.sa_eg_p6_paymybuddy.dal.repository;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
