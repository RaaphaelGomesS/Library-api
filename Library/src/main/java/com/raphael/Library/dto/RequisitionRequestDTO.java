package com.raphael.Library.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionRequestDTO {

    private UUID bookId;

    private UUID associateId;

    private String action;

    private LocalDate retiredDate;
}
