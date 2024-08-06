package com.raphael.Library.controller;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
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

    private AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<List<Associate>> getAllAssociate() {

        List<Associate> associate = repository.findAll();

        return ResponseEntity.ok(associate);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable long id, @RequestHeader String token) throws Exception {

        authenticationService.validateToken(token);

        Associate associate = service.getById(id);

        return ResponseEntity.ok(associate);

    }

    @PutMapping("/update")
    public ResponseEntity<AssociateResponseDTO> updateAssociate(@RequestBody AssociateRequestDTO associateRequestDTO, @RequestHeader String token) throws Exception {

        authenticationService.validateToken(token);

        AssociateResponseDTO associate = service.updateAssociate(associateRequestDTO);

        return ResponseEntity.ok(associate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable long id, @RequestHeader String token) throws Exception {

        authenticationService.validateToken(token);

        service.deleteAssociate(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
