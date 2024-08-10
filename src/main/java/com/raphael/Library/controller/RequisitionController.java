package com.raphael.Library.controller;

import com.raphael.Library.dto.requisition.RequisitionPageDTO;
import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AuthenticationService;
import com.raphael.Library.service.RequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requisition")
@RequiredArgsConstructor
public class RequisitionController {

    private RequisitionService requisitionService;

    private AssociateRepository associateRepository;

    private AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<RequisitionPageDTO> getAllRequisitionCloseToExpire(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        RequisitionPageDTO pageDTO = requisitionService.getRequisitionCloseToExpire(page, pageSize);

        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/")
    public ResponseEntity<RequisitionResponseDTO> makeRequisition(@RequestBody RequisitionRequestDTO requestDTO, @RequestHeader String token) throws Exception {

        String tokenId = authenticationService.validateToken(token);

        RequisitionResponseDTO responseDTO = requisitionService.makeRequisitionByAction(requestDTO, tokenId);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RequisitionResponseDTO>> getAllRequisitionFromAssociate(@PathVariable long id, @RequestHeader String token) throws Exception {

        String tokenId = authenticationService.validateToken(token);

        List<RequisitionResponseDTO> responseDTOS = requisitionService.getRequisitionForAssociate(id, tokenId);

        return ResponseEntity.ok(responseDTOS);
    }
}
