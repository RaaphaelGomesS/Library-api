package com.raphael.Library.dto.associate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssociateRequestDTO {

    private long id;

    private String name;

    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;
}
