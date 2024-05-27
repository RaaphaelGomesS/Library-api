package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private AssociateService service;

    @GetMapping("/")
    public ResponseEntity<Associate> getAllAssociates() {


    }

    @PostMapping("/create")
    public ResponseEntity<Associate> createAssociate(@RequestBody AssociateDTO associateDTO) throws AssociateException {

        Associate associate = service.createAssociate(associateDTO);

        return ResponseEntity.ok(associate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable UUID associateId) {

    }

    @PutMapping("/alter")
    public ResponseEntity<Associate> updateAssociate(@RequestBody AssociateDTO associateDTO) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociate(@PathVariable UUID associateId) {

    }
}
