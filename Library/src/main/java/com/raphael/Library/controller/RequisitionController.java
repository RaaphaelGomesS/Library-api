package com.raphael.Library.controller;

import com.raphael.Library.dto.RequisitionPageDTO;
import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.RequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requisition")
@RequiredArgsConstructor
public class RequisitionController {

    private RequisitionService requisitionService;

    private AssociateRepository associateRepository;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('role_ADMIN')")
    public ResponseEntity<RequisitionPageDTO> getAllRequisitionCloseToExpire(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        RequisitionPageDTO pageDTO = requisitionService.getRequisitionCloseToExpire(page, pageSize);

        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/")
    public ResponseEntity<RequisitionResponseDTO> makeRequisition(@RequestBody RequisitionRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        RequisitionResponseDTO responseDTO = requisitionService.makeRequisitionByAction(requestDTO, token);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RequisitionResponseDTO>> getAllRequisitionFromAssociate(@PathVariable long id, JwtAuthenticationToken token) throws Exception {

        List<RequisitionResponseDTO> responseDTOS = requisitionService.getRequisitionForAssociate(id, token);

        return ResponseEntity.ok(responseDTOS);
    }
}
