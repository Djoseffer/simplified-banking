package com.djoseffer.desafio.domain.entities;

import com.djoseffer.desafio.domain.entities.enums.UserType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "userData")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityUserAccount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private Long accountNumber;
    @NotNull
    private Long agency;
    @NotNull
    private BigDecimal balance;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private EntityUsers entityUser;
}
