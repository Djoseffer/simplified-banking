package com.djoseffer.desafio.domain.dtos;

import java.math.BigDecimal;

public record PixDTO(String namePayer, String nameReceiver, BigDecimal pixValue, String documentPayer, String documentReceiver) {
}
