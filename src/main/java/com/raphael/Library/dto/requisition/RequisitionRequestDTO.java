package com.raphael.Library.dto.requisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitionRequestDTO {

    private long requisitionId;

    private long bookId;

    private long associateId;

    private String action;

    private LocalDate retiredDate;
}
