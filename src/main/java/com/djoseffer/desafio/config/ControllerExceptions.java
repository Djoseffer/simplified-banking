package com.djoseffer.desafio.config;

import com.djoseffer.desafio.domain.dtos.ExceptionDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.DuplicateFormatFlagsException;

@RestControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity threatDataEmpty() {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Field must not be empty", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(DuplicateFormatFlagsException.class)
    public ResponseEntity threatDuplicateRegister() {
        ExceptionDTO exceptionDTO = new ExceptionDTO("The document is already registered", "409");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity threatFormatError() {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                "Error registering customer. Check the fields.\n" +
                        "The type field must be ACCOUNTHOLDER or SAVINGSHOLDER", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
