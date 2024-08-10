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

    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<List<Associate>> getAllAssociate() {

        List<Associate> associate = repository.findAll();

        return ResponseEntity.ok(associate);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable long id, @RequestHeader String auth) throws Exception {

        String tokenId = authenticationService.validateToken(auth);

        Associate associate = service.getById(id, tokenId);

        return ResponseEntity.ok(associate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AssociateResponseDTO> updateAssociate(@PathVariable long id, @RequestBody AssociateRequestDTO associateRequestDTO, @RequestHeader String auth) throws Exception {

        String tokenId = authenticationService.validateToken(auth);

        AssociateResponseDTO associate = service.updateAssociate(associateRequestDTO, id, tokenId);

        return ResponseEntity.ok(associate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable long id, @RequestHeader String auth) throws Exception {

        String tokenId = authenticationService.validateToken(auth);

        service.deleteAssociate(id, tokenId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
