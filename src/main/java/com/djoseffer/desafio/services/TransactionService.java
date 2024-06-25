package com.djoseffer.desafio.services;

import com.djoseffer.desafio.controllers.TransactionsController;
import com.djoseffer.desafio.domain.entities.EntityTransaction;
import com.djoseffer.desafio.domain.entities.EntityUserAccount;
import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.domain.entities.enums.TransactionType;
import com.djoseffer.desafio.repositories.TransactionRepository;
import com.djoseffer.desafio.repositories.UserDataRepository;
import com.djoseffer.desafio.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<EntityTransaction> findAll(Pageable pageable) {
        Page<EntityTransaction> listAll = transactionRepository.findAll(pageable);
        for (EntityTransaction entity : listAll) {
            long id = entity.getId();
            entity.add(linkTo(methodOn(TransactionsController.class).getOne(id)).withSelfRel());
        }
        return listAll;
    }

    public Optional<EntityTransaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public void save(EntityTransaction entity) {
        validateTransaction(entity);
        processTransaction(entity);
        transactionRepository.save(entity);
    }

    private void validateTransaction(EntityTransaction entity) {
        if (entity.getTransactionType() != TransactionType.WITHDRAWAL &&
                entity.getTransactionType() != TransactionType.DEPOSIT) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        Optional<EntityUsers> userOptional = Optional.ofNullable(userRepository.findByDocument(entity.getDocument()));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        EntityUsers user = userOptional.get();
        Optional<EntityUserAccount> accountOptional = user.getUserAccounts().stream()
                .filter(account -> account.getAccountNumber().equals(entity.getAccountNumber()))
                .findFirst();

        if (accountOptional.isEmpty()) {
            throw new IllegalArgumentException("Account number does not match the document");
        }

        EntityUserAccount account = accountOptional.get();
        if (entity.getTransactionType() == TransactionType.WITHDRAWAL) {
            if (entity.getValue().compareTo(account.getBalance()) > 0) {
                throw new IllegalArgumentException("Insufficient balance for withdrawal");
            }
        }
    }

    @Transactional
    public void processTransaction(EntityTransaction entity) {
        Optional<EntityUsers> userOptional = Optional.ofNullable(userRepository.findByDocument(entity.getDocument()));
        EntityUsers user = userOptional.get();

        EntityUserAccount account = user.getUserAccounts().stream()
                .filter(a -> a.getAccountNumber().equals(entity.getAccountNumber()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account number does not match the document"));

        if (entity.getTransactionType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(entity.getValue()));
            user.setBalance(user.getBalance().add(entity.getValue()));
        } else if (entity.getTransactionType() == TransactionType.WITHDRAWAL) {
            account.setBalance(account.getBalance().subtract(entity.getValue()));
            user.setBalance(user.getBalance().subtract(entity.getValue()));
        }

        userDataRepository.save(account);
        userRepository.save(user);
    }

    public BigDecimal getUpdatedBalance(String document) {
        Optional<EntityUsers> userOptional = Optional.ofNullable(userRepository.findByDocument(document));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        EntityUsers user = userOptional.get();
        return user.getBalance();
    }
}
