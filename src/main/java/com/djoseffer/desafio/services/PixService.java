package com.djoseffer.desafio.services;

import com.djoseffer.desafio.domain.entities.EntityPix;
import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.repositories.PixRepository;
import com.djoseffer.desafio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PixService {
    @Autowired
    private PixRepository pixRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(EntityPix newPix) {
        EntityUsers payer = userRepository.findByDocument(newPix.getDocumentPayer());
        EntityUsers receiver = userRepository.findByDocument(newPix.getDocumentReceiver());

        if (payer == null || receiver == null) {
            throw new IllegalArgumentException("One or both documents do not exist");
        }
        if (payer.getBalance().compareTo(newPix.getPixValue()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        payer.setBalance(payer.getBalance().subtract(newPix.getPixValue()));
        receiver.setBalance(receiver.getBalance().add(newPix.getPixValue()));

        userRepository.save(payer);
        userRepository.save(receiver);

        pixRepository.save(newPix);
    }

    public Page<EntityPix> findAll(Pageable pageable) {
        return pixRepository.findAll(pageable);
    }
}
