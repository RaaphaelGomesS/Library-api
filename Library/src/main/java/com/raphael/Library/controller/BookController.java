package com.raphael.Library.controller;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    //admin

    @PostMapping("/")
    @PreAuthorize("hasAuthority('role_ADMIN')")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) throws BookException {

        Book book = bookService.createBook(bookDTO);

        return ResponseEntity.ok(book);
    }

    //private

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks(JwtAuthenticationToken token) {

        List<Book> books = bookRepository.findAll();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getAllBooksFromAuthor(@RequestBody String authorName) throws BookException {

        List<Book> book = bookService.getAllBooksByAuthor(authorName);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<Book>> getAllBooksFromPublisher(@RequestBody String publisherName) throws BookException {

        List<Book> book = bookService.getAllBooksByPublisher(publisherName);

        return ResponseEntity.ok(book);
    }
}