package com.raphael.Library.controller;

import com.raphael.Library.dto.requisition.RequisitionPageDTO;
import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.service.AuthenticationService;
import com.raphael.Library.service.RequisitionService;
import mock.entities.AssociateMock;
import mock.request.RequisitionRequestDTOMock;
import mock.response.RequisitionResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequisitionControllerTest {

    @InjectMocks
    private RequisitionController controller;

    @Mock
    private RequisitionService requisitionService;

    @Mock
    private AuthenticationService authenticationService;

    private RequisitionPageDTO pageDTO;

    private Associate associate;

    private RequisitionResponseDTO responseDTO;

    private List<RequisitionResponseDTO> responseDTOS;

    private RequisitionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        associate = AssociateMock.toEntity();
        pageDTO = mock(RequisitionPageDTO.class);
        requestDTO = RequisitionRequestDTOMock.toRequest();
        responseDTOS = RequisitionResponseDTOMock.toList();
        responseDTO = RequisitionResponseDTOMock.toResponseDTO();
    }

    @Test
    void shouldGetAllRequisitionCloseToExpire() {
        assertDoesNotThrow(() -> {

            ResponseEntity<RequisitionPageDTO> responseEntity = ResponseEntity.ok(pageDTO);

            when(requisitionService.getRequisitionCloseToExpire(anyInt(), anyInt())).thenReturn(pageDTO);

            ResponseEntity<RequisitionPageDTO> result = controller.getAllRequisitionCloseToExpire(1, 10);

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldMakeRequisition() {
        assertDoesNotThrow(() -> {

            ResponseEntity<RequisitionResponseDTO> responseEntity = ResponseEntity.ok(responseDTO);

            when(authenticationService.validateToken(any())).thenReturn(associate);
            when(requisitionService.makeRequisitionByAction(any(), any())).thenReturn(responseDTO);

            ResponseEntity<RequisitionResponseDTO> result = controller.makeRequisition(requestDTO, "token");

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldGetAllRequisitionFromAssociate() {
        assertDoesNotThrow(() -> {

            ResponseEntity<List<RequisitionResponseDTO>> responseEntity = ResponseEntity.ok(responseDTOS);

            when(authenticationService.validateToken(any())).thenReturn(associate);
            when(requisitionService.getRequisitionForAssociate(anyLong(), any())).thenReturn(responseDTOS);

            ResponseEntity<?> result = controller.getAllRequisitionFromAssociate(1L, "token");

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldReturnStringWhenCantFindAnyRequisition() {
        assertDoesNotThrow(() -> {

            ResponseEntity<String> responseEntity = ResponseEntity.ok("No have any requisition!");

            when(authenticationService.validateToken(any())).thenReturn(associate);
            when(requisitionService.getRequisitionForAssociate(anyLong(), any())).thenReturn(Collections.emptyList());

            ResponseEntity<?> result = controller.getAllRequisitionFromAssociate(1L, "token");

            assertEquals(responseEntity, result);

        });
    }
}
