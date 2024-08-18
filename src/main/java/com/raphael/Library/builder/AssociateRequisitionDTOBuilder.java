package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateRequisitionDTO;
import com.raphael.Library.dto.associate.BooksInPossessionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssociateRequisitionDTOBuilder {

    public static AssociateRequisitionDTO from(Associate associate) {
        return AssociateRequisitionDTO.builder()
                .associateId(associate.getAssociateId())
                .requisitions(associate.getBooksInPossession()
                        .stream().map(AssociateRequisitionDTOBuilder::buildBookInPossession).toList())
                .build();
    }

    private static BooksInPossessionResponseDTO buildBookInPossession(Requisition requisition) {
        return BooksInPossessionResponseDTO
                .builder()
                .requisitionId(requisition.getRequisitionId())
                .bookName(requisition.getBook().getName())
                .status(requisition.getStatusIndicator())
                .retiredDate(requisition.getRetiredDate())
                .updateDate(requisition.getUpdateDate())
                .devolutionDate(requisition.getDevolutionDate())
                .build();
    }
}
