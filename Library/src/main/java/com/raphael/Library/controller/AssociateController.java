package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private AssociateService service;

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable UUID id) throws AssociateException {

        Associate associate = service.getById(id);

        return ResponseEntity.ok(associate);

    }

    @PostMapping("/create")
    public ResponseEntity<Associate> createAssociate(@RequestBody AssociateDTO associateDTO) throws AssociateException {

        Associate associate = service.createAssociate(associateDTO);

        return ResponseEntity.ok(associate);
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<Associate> updateAssociate(@PathVariable UUID id, @RequestBody AssociateDTO associateDTO) throws AssociateException {

        Associate associate = service.updateAssociate(id, associateDTO);

        return ResponseEntity.ok(associate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable UUID id) throws AssociateException {

        service.deleteAssociate(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
