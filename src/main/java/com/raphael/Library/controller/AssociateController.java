package com.raphael.Library.controller;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Busca todos os associados cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista todos os associados."),
            @ApiResponse(responseCode = "404", description = "Nenhum associado encontrado.")
    })
    public ResponseEntity<List<Associate>> getAllAssociate() throws AssociateException {

        List<Associate> associate = repository.findAll();

        if (associate.isEmpty()) {
            throw new AssociateException("Nenhum associado encontrado.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(associate);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca associado por id, apenas admin pode buscar outro perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o associado."),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não possui permissão.")
    })
    public ResponseEntity<Associate> getAssociate(@PathVariable long id, @RequestHeader String auth) throws Exception {

        Associate associateByToken = authenticationService.validateToken(auth);

        Associate associate = service.getById(id, associateByToken);

        return ResponseEntity.ok(associate);
    }

    @PutMapping("/update")
    @Operation(summary = "Atualiza associado, somente admin pode alterar outro associado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna associado atualizado."),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não possui permissão.")
    })
    public ResponseEntity<AssociateResponseDTO> updateAssociate(@RequestBody AssociateRequestDTO associateRequestDTO, @RequestHeader String auth) throws Exception {

        Associate associateByToken = authenticationService.validateToken(auth);

        AssociateResponseDTO associate = service.updateAssociate(associateRequestDTO, associateByToken);

        return ResponseEntity.ok(associate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta associado pelo Id, somente admin pode alterar outro associado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado deletado."),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não possui permissão.")
    })
    public ResponseEntity<String> deleteAssociate(@PathVariable long id, @RequestHeader String auth) throws Exception {

        Associate associateByToken = authenticationService.validateToken(auth);

        service.deleteAssociate(id, associateByToken);

        return ResponseEntity.ok("Associado deletado, id: " + id);
    }
}
