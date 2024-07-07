package com.raphael.Library.builder;

import com.raphael.Library.dto.BookRequestDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.entities.books.Publisher;
import com.raphael.Library.indicator.GenderIndicator;
import com.raphael.Library.entities.books.Book;

public class BookBuilder {

    public static Book from(BookRequestDTO bookRequestDTO, Author author, Publisher publisher) {

        return Book.builder()
                .name(bookRequestDTO.getBookName())
                .gender(GenderIndicator.getValueByName(bookRequestDTO.getGender()))
                .author(author)
                .publisher(publisher)
                .build();
    }
}
