package com.djoseffer.desafio.services;

import com.djoseffer.desafio.domain.entities.EntityTransaction;
import com.djoseffer.desafio.domain.entities.EntityUserAccount;
import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.domain.entities.TransactionType;
import com.djoseffer.desafio.repositories.TransactionRepository;
import com.djoseffer.desafio.repositories.UserDataRepository;
import com.djoseffer.desafio.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EntityTransaction> findAll() {
        return transactionRepository.findAll();
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

        Optional<EntityUsers> userOptional = userRepository.findByDocument(entity.getDocument());
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
        Optional<EntityUsers> userOptional = userRepository.findByDocument(entity.getDocument());
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
        Optional<EntityUsers> userOptional = userRepository.findByDocument(document);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        EntityUsers user = userOptional.get();
        return user.getBalance();
    }
}
