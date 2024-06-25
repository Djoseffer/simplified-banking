package com.djoseffer.desafio.controllers;

import com.djoseffer.desafio.domain.dtos.PixDTO;
import com.djoseffer.desafio.domain.entities.EntityPix;
import com.djoseffer.desafio.services.PixService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("transfers")
public class PixController {

    @Autowired
    public PixService pixService;

    @PostMapping
    public ResponseEntity<EntityPix> postPix(@RequestBody PixDTO pixDTO) {
        var newPix = new EntityPix();
        BeanUtils.copyProperties(pixDTO, newPix);
        pixService.save(newPix);
        newPix.setDateStamp(LocalDateTime.now(ZoneId.of("UTF")));
        return ResponseEntity.status(HttpStatus.CREATED).body(newPix);
    }

    @GetMapping
    public ResponseEntity<Page<EntityPix>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAll(pageable));
    }
}
