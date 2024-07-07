package com.raphael.Library.controller;

import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.service.RequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requisition")
@RequiredArgsConstructor
public class RequisitionController {

    private RequisitionService requisitionService;

    @PostMapping("/")
    public ResponseEntity<RequisitionResponseDTO> makeRequisition(@RequestBody RequisitionRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        RequisitionResponseDTO responseDTO = requisitionService.makeRequisitionByAction(requestDTO, token);

        return ResponseEntity.ok(responseDTO);
    }

    // todo 07/07/24 requisition por associado, todas as requisições e requisições que estão perto de expirar


}
