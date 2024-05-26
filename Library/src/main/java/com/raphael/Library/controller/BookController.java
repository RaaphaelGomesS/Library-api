package com.raphael.Library.controller;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) throws BookException {

        Book book = bookService.createBook(bookDTO);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/")
    public ResponseEntity<Book> getAllBooks(@RequestBody BookDTO bookDTO) throws BookException {

        Book book = bookService.createBook(bookDTO);

        return ResponseEntity.ok(book);
    }
}