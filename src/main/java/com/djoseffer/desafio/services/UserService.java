package com.djoseffer.desafio.services;

import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Page<EntityUsers> getFindAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<EntityUsers> findById(Long id) {
        var getId = repository.findById(id);
        if (getId.isPresent()) {
            return repository.findById(id);
        }
        throw new IllegalArgumentException("Id not found");
    }

    @Transactional
    public void saveUser(EntityUsers entityAccount) {
        if (repository.existsByDocument(entityAccount.getDocument())) {
            throw new DuplicateFormatFlagsException("Duplicate document");
        }
        repository.save(entityAccount);
    }

    @Transactional
    public void delete(Long id) {
        Optional<EntityUsers> deleteId = repository.findById(id);
        if (deleteId.isEmpty()) {
            throw new IllegalArgumentException("Id not found");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void update(EntityUsers updatedUser) {
        repository.save(updatedUser);
    }
}
