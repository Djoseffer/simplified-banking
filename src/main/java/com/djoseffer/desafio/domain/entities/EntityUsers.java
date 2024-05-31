package com.djoseffer.desafio.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "usuarios_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityUsers {
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
    @NotBlank
    @Column(unique = true)
    private String document;
    private BigDecimal balance;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "entityUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EntityUserAccount> userAccounts;
}
