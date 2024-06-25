package com.djoseffer.desafio.repositories;

import com.djoseffer.desafio.domain.entities.EntityPix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<EntityPix, Long> {
}
