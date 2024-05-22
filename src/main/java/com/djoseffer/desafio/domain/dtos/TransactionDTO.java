package com.djoseffer.desafio.domain.dtos;

import com.djoseffer.desafio.domain.entities.TransactionType;

import java.math.BigDecimal;

public record TransactionDTO(String firstName, String lastName, Long accountNumber, String document, TransactionType transactionType, BigDecimal value) {
}
