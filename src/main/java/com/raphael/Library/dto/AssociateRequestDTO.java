package com.raphael.Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateRequestDTO {

    private long associateId;

    private String name;

    private String email;

    private String username;

    private String password;
}
