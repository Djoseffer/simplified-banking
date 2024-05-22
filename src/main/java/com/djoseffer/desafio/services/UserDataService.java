package com.djoseffer.desafio.services;

import com.djoseffer.desafio.domain.entities.EntityUserAccount;
import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.repositories.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepository userDataRepository;

    private static final Random RANDOM = new Random();

    public static Long generateAccountNumber() {
        return (long) (RANDOM.nextInt(900000) + 100000);
    }

    public static Long generateAgency() {
        return (long) (RANDOM.nextInt(90) + 10);
    }

    @Transactional
    public void createAndSaveUserAccount(EntityUsers entityUser) {
        if (entityUser == null) {
            throw new IllegalArgumentException("EntityUser cannot be null");
        }

        EntityUserAccount userAccount = new EntityUserAccount();
        userAccount.setAccountNumber(generateAccountNumber());
        userAccount.setAgency(generateAgency());
        userAccount.setType(entityUser.getType());
        userAccount.setBalance(entityUser.getBalance());
        userAccount.setEntityUser(entityUser);

        userDataRepository.save(userAccount);
    }
}
