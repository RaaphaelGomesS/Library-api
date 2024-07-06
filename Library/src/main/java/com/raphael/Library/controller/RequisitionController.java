package com.raphael.Library.controller;

import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.repository.RequisitionRepository;
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

    //private

    @PostMapping("/")
    public ResponseEntity<RequisitionResponseDTO> makeRequisition(@RequestBody RequisitionRequestDTO requestDTO) throws Exception {

        RequisitionResponseDTO responseDTO = requisitionService.makeRequisitionByAction(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    // todo 07/07/24 requisition por associado, todas as requisições e requisições que estão perto de expirar


}
