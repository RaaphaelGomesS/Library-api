package com.raphael.Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String bookName;

    private String authorName;

    private String departmentName;

    private String publisherName;
}
