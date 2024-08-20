package com.raphael.Library.dto.book;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank
    private String bookName;

    private String gender;

    @NotBlank
    private String authorName;

    @NotBlank
    private String publisherName;
}
