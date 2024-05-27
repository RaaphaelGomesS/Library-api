package com.raphael.Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateDTO {

    private String name;

    private String email;

    private String phone;
}
