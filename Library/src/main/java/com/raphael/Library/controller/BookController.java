package com.raphael.Library.controller;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class BookController {

    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) throws BookException {

        Book book = bookService.createBook(bookDTO);

        return ResponseEntity.ok(book);
    }
}