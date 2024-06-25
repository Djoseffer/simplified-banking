package com.djoseffer.desafio.domain.dtos;

import com.djoseffer.desafio.domain.entities.enums.UserType;

import java.math.BigDecimal;

public record UsersDTO(String firstName, String lastName, String document, UserType type, BigDecimal balance) {
}
