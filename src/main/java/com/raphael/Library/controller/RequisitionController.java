package com.raphael.Library.controller;

import com.raphael.Library.dto.requisition.RequisitionPageDTO;
import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.RequisitionException;
import com.raphael.Library.service.AuthenticationService;
import com.raphael.Library.service.RequisitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requisition")
@RequiredArgsConstructor
public class RequisitionController {

    private final RequisitionService requisitionService;

    private final AuthenticationService authenticationService;

    @GetMapping("/")
    @Operation(summary = "Busca todas as requisições por paginas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todos as requisições."),
            @ApiResponse(responseCode = "404", description = "Nenhuma requisição encontrada.")
    })
    public ResponseEntity<RequisitionPageDTO> getAllRequisitionCloseToExpire(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws RequisitionException {

        RequisitionPageDTO pageDTO = requisitionService.getRequisitionCloseToExpire(page, pageSize);

        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/")
    @Operation(summary = "Cria uma nova requisição.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna a requisição criada."),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado."),
            @ApiResponse(responseCode = "409", description = "Não é possivel realizar essa ação, tente: abrir, renovar ou fechar.")
    })
    public ResponseEntity<RequisitionResponseDTO> makeRequisition(@RequestBody RequisitionRequestDTO requestDTO, @RequestHeader String auth) throws Exception {

        Associate associateByToken = authenticationService.validateToken(auth);

        RequisitionResponseDTO responseDTO = requisitionService.makeRequisitionByAction(requestDTO, associateByToken);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca todas as requisições do associado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todas as requisição do associado."),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado."),
            @ApiResponse(responseCode = "409", description = "Não é possivel realizar essa ação, tente: abrir, renovar ou fechar.")
    })
    public ResponseEntity<?> getAllRequisitionFromAssociate(@PathVariable long id, @RequestHeader String auth) throws Exception {

        Associate associateByToken = authenticationService.validateToken(auth);

        List<RequisitionResponseDTO> responseDTOS = requisitionService.getRequisitionForAssociate(id, associateByToken);

        return responseDTOS.isEmpty() ? ResponseEntity.ok().body("No have any requisition!") : ResponseEntity.ok(responseDTOS);
    }
}
