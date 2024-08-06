package com.raphael.Library.dto.book;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    private String bookName;

    private String gender;

    private String authorName;

    private String publisherName;
}
