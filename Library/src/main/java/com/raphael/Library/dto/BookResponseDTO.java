package com.raphael.Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private Long bookId;

    private String name;

    private String author;

    private String publisher;
}
