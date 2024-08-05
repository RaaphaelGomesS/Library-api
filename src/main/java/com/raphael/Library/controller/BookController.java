package com.raphael.Library.controller;

import com.raphael.Library.builder.BookResponseDTOBuilder;
import com.raphael.Library.dto.BookRequestDTO;
import com.raphael.Library.dto.BookResponseDTO;
import com.raphael.Library.dto.GenderFilter;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO bookRequestDTO) throws BookException {

        Book book = bookService.createBook(bookRequestDTO);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        List<BookResponseDTO> books = bookRepository.findAll()
                .stream()
                .map(BookResponseDTOBuilder::from)
                .toList();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/gender")
    public ResponseEntity<List<BookResponseDTO>> getAllBooksByGender(@RequestBody GenderFilter filter) throws BookException {

        List<BookResponseDTO> bookResponseDTOS = bookService.getBooksByGender(filter);

        return ResponseEntity.ok(bookResponseDTOS);
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookResponseDTO>> getAllBooksFromAuthor(@RequestParam("author") String authorName) throws BookException {

        List<BookResponseDTO> book = bookService.getAllBooksByAuthor(authorName);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<BookResponseDTO>> getAllBooksFromPublisher(@RequestParam("publisher") String publisherName) throws BookException {

        List<BookResponseDTO> book = bookService.getAllBooksByPublisher(publisherName);

        return ResponseEntity.ok(book);
    }
}