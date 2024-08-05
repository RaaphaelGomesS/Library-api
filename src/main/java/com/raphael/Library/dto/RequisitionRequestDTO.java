package com.raphael.Library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionRequestDTO {

    private long requisitionId;

    private long bookId;

    private long associateId;

    private String action;

    private LocalDate retiredDate;
}
