package com.raphael.Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String bookName;

    private String gender;

    private String authorName;

    private String publisherName;
}
