package com.djoseffer.desafio.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "transactions")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    private Long accountNumber;
    @NotNull
    @NotBlank
    private String document;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @NotNull
    @Positive
    private BigDecimal value;
}
