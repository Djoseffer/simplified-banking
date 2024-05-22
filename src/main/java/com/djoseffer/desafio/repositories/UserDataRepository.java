package com.djoseffer.desafio.repositories;

import com.djoseffer.desafio.domain.entities.EntityUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<EntityUserAccount, Long> {
}
