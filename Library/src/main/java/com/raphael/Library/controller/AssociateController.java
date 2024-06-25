package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateService service;

    private final AssociateRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Associate>> getAllAssociate() {

        List<Associate> associate = repository.findAll();

        return ResponseEntity.ok(associate);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable long id) throws AssociateException {

        Associate associate = service.getById(id);

        return ResponseEntity.ok(associate);

    }

    @PostMapping("/create")
    public ResponseEntity<Associate> createAssociate(@RequestBody AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Associate associate = service.createAssociate(associateRequestDTO);

        return ResponseEntity.ok(associate);
    }

    @PutMapping("/alter")
    public ResponseEntity<Associate> updateAssociate(@RequestBody AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Associate associate = service.updateAssociate(associateRequestDTO);

        return ResponseEntity.ok(associate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable long id) throws AssociateException {

        service.deleteAssociate(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
