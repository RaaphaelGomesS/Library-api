package com.raphael.Library.builder;

import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.entities.Requisition;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequisitionResponseDTOBuilder {

    public static RequisitionResponseDTO from(Requisition requisition) {

        return RequisitionResponseDTO
                .builder()
                .requisitionId(requisition.getRequisitionId())
                .statusIndicator(requisition.getStatusIndicator())
                .retiredDate(requisition.getRetiredDate())
                .devolutionDate(requisition.getDevolutionDate())
                .build();
    }
}
