package com.djoseffer.desafio.domain.dtos;

import com.djoseffer.desafio.domain.entities.enums.UserType;

public record UpdateDTO(String firstName, String lastName, UserType type) {
}
