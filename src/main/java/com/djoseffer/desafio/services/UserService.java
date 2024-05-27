package com.djoseffer.desafio.services;

import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<EntityUsers> getFindAll() {
        return repository.findAll();
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
        if (!repository.existsByDocument(entityAccount.getDocument())) {
            repository.save(entityAccount);
        } else {
            throw new DuplicateFormatFlagsException("Duplicate document ");
        }
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
    public void update(EntityUsers entityUsers) {
        repository.save(entityUsers);
    }
}
