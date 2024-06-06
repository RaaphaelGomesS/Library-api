package com.raphael.Library.builder;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.indicator.GenderIndicator;
import com.raphael.Library.entities.books.Book;

public class BookBuilder {

    public static Book from(BookDTO bookDTO) {

        return Book.builder()
                .name(bookDTO.getBookName())
                .gender(GenderIndicator.getValueByName(bookDTO.getGender()))
                .build();
    }
}
