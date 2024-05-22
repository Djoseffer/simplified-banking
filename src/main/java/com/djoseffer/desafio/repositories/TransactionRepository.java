package com.djoseffer.desafio.repositories;

import com.djoseffer.desafio.domain.entities.EntityTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<EntityTransaction, Long> {
}
