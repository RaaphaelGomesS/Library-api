package com.raphael.Library.builder;

import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.entities.Requisition;
import org.apache.coyote.Request;

public class RequisitionResponseDTOBuilder {

    public static RequisitionResponseDTO from(Requisition requisition) {

        return RequisitionResponseDTO
                .builder()
                .requisitionId(requisition.getRequisitionId())
                .statusIndicator(requisition.getStatusIndicator())
                .devolutionDate(requisition.getDevolutionDate())
                .build();
    }
}
