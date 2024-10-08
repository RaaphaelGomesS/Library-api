package com.raphael.Library.builder;

import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.entities.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookResponseDTOBuilder {

    public static List<BookResponseDTO> fromList(List<Book> books) {
        return books.stream().map(BookResponseDTOBuilder::from).collect(Collectors.toList());
    }

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO
                .builder()
                .bookId(book.getBookId())
                .name(book.getName())
                .gender(book.getGender().name())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .build();
    }
}
