package com.djoseffer.desafio.controllers;

import com.djoseffer.desafio.domain.dtos.TransactionDTO;
import com.djoseffer.desafio.domain.entities.EntityTransaction;
import com.djoseffer.desafio.services.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
            transaction.setDateStamp(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Page<EntityTransaction>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        Optional<EntityTransaction> getId = transactionService.findById(id);
        getId.get().add(linkTo(methodOn(TransactionsController.class).getAll(Pageable.unpaged())).withRel("Transactions list"));
        return getId.<ResponseEntity<Object>>map(entityTransaction ->
                        ResponseEntity.status(HttpStatus.OK).body(entityTransaction))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Id not found"));
    }
}
