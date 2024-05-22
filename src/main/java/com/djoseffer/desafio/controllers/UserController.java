package com.djoseffer.desafio.controllers;

import com.djoseffer.desafio.domain.entities.EntityUsers;
import com.djoseffer.desafio.domain.dtos.UsersDTO;
import com.djoseffer.desafio.services.UserDataService;
import com.djoseffer.desafio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserDataService userDataService;

    @PostMapping
    public ResponseEntity<EntityUsers> userRegister(@RequestBody @Valid UsersDTO usersDTO) {
        var createUser = new EntityUsers();
        BeanUtils.copyProperties(usersDTO, createUser);
        userService.saveUser(createUser);
        userDataService.createAndSaveUserAccount(createUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    @GetMapping
    public ResponseEntity<List<EntityUsers>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFindAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        Optional<EntityUsers> getClient = userService.findById(id);
        return getClient.<ResponseEntity<Object>>map(entityUsers -> ResponseEntity
                        .status(HttpStatus.OK).body(entityUsers))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found"));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Optional<EntityUsers> deleteId = userService.findById(id);
        if (deleteId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete user successfully");
    }
}