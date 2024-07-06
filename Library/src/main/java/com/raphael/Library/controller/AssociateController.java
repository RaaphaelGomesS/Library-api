package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.dto.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateService service;

    private final AssociateRepository repository;

    //admin

    @GetMapping("/")
    @PreAuthorize("hasAuthority('role_ADMIN')")
    public ResponseEntity<List<Associate>> getAllAssociate() {

        List<Associate> associate = repository.findAll();

        return ResponseEntity.ok(associate);

    }

    //admin e private

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable long id, JwtAuthenticationToken token) throws AssociateException {

        Associate associate = service.getById(id);

        return ResponseEntity.ok(associate);

    }

    @PutMapping("/alter")
    public ResponseEntity<AssociateResponseDTO> updateAssociate(@RequestBody AssociateRequestDTO associateRequestDTO, JwtAuthenticationToken token) throws AssociateException {

        AssociateResponseDTO associate = service.updateAssociate(associateRequestDTO);

        return ResponseEntity.ok(associate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable long id, JwtAuthenticationToken token) throws AssociateException {

        service.deleteAssociate(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
