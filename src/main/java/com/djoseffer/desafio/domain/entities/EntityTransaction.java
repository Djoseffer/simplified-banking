package com.djoseffer.desafio.domain.entities;

import com.djoseffer.desafio.domain.entities.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityTransaction extends RepresentationModel<EntityTransaction> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
    @DateTimeFormat
    private LocalDateTime dateStamp;
}
