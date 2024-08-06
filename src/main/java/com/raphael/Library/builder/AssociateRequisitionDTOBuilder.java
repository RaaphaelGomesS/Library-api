package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateRequisitionDTO;
import com.raphael.Library.entities.Associate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssociateRequisitionDTOBuilder {

    public static AssociateRequisitionDTO from(Associate associate) {
        return AssociateRequisitionDTO.builder()
                .associateId(associate.getAssociateId())
                .requisitions(associate.getBooksInPossession())
                .build();
    }
}
