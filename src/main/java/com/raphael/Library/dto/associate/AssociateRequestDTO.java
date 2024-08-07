package com.raphael.Library.dto.associate;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateRequestDTO {

    private String name;

    private String email;

    private String username;

    private String password;
}
