package com.djoseffer.desafio.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers_pix")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityPix implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String namePayer;
    @NotBlank
    @NotNull
    private String nameReceiver;
    @NotNull
    private BigDecimal pixValue;
    @NotNull
    @NotBlank
    private String documentPayer;
    @NotNull
    @NotBlank
    private String documentReceiver;
    LocalDateTime dateStamp;
}
