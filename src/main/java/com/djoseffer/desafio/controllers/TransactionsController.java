package com.djoseffer.desafio.controllers;

import com.djoseffer.desafio.domain.dtos.TransactionDTO;
import com.djoseffer.desafio.domain.entities.EntityTransaction;
import com.djoseffer.desafio.services.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> newTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            var transaction = new EntityTransaction();
            BeanUtils.copyProperties(transactionDTO, transaction);
            transactionService.save(transaction);

            BigDecimal updatedBalance = transactionService.getUpdatedBalance(transactionDTO.document());

            Map<String, Object> response = new HashMap<>();
            response.put("transaction", transaction);
            response.put("updatedBalance", updatedBalance);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<EntityTransaction>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        Optional<EntityTransaction> getId = transactionService.findById(id);
        return getId.<ResponseEntity<Object>>map(entityTransaction ->
                        ResponseEntity.status(HttpStatus.OK).body(entityTransaction))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Id not found"));
    }
}
