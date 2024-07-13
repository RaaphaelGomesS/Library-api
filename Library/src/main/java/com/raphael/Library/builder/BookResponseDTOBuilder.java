package com.raphael.Library.builder;

import com.raphael.Library.dto.BookResponseDTO;
import com.raphael.Library.entities.books.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookResponseDTOBuilder {

    public static List<BookResponseDTO> fromList(List<Book> books) {
        return books.stream().map(BookResponseDTOBuilder::from).collect(Collectors.toList());
    }

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO
                .builder()
                .bookId(book.getBookId())
                .name(book.getName())
                .author(book.getAuthor().getName())
                .publisher(book.getPublisher().getName())
                .build();
    }
}
