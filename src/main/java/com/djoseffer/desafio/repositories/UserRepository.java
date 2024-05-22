package com.djoseffer.desafio.repositories;

import com.djoseffer.desafio.domain.entities.EntityUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EntityUsers,Long> {
    Optional<EntityUsers> findByDocument(String document);
    boolean existsByDocument(String document);
}
