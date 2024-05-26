package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/associate")
public class AssociateController {

    public ResponseEntity<Associate> createAssociate(@RequestBody AssociateDTO associateDTO) {
        
    }
}
