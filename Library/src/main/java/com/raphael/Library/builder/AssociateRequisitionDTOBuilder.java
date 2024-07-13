package com.raphael.Library.builder;

import com.raphael.Library.dto.AssociateRequisitionDTO;
import com.raphael.Library.entities.Associate;

public class AssociateRequisitionDTOBuilder {

    public static AssociateRequisitionDTO from(Associate associate) {
        return AssociateRequisitionDTO.builder()
                .associateId(associate.getAssociateId())
                .requisitions(associate.getBooksInPossession())
                .build();
    }
}
