package com.djoseffer.desafio.domain.dtos;

import com.djoseffer.desafio.domain.entities.UserType;

public record UpdateDTO(String firstName, String lastName, UserType type) {
}
