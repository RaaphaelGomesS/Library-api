package com.raphael.Library.dto.requisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitionRequestDTO {

    private String requisitionId;

    @NotBlank
    private String bookName;

    @NotBlank
    private String associate;

    @NotBlank
    private String action;
}
