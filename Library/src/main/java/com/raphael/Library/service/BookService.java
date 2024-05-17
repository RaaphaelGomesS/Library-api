package com.raphael.Library.service;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository repository;


    public Book createBook(BookDTO newBook) throws BookException {

        verifyIfAlreadyExist(newBook);




    }

    private void verifyIfAlreadyExist(BookDTO bookDTO) throws BookException {

        Optional<Book> foundedBook = repository.getBookByTheName(bookDTO.getBookName());

        if (foundedBook.isPresent()) {
            if (foundedBook.get().getAuthor().getName().equals(bookDTO.getAuthorName())) {
                throw new BookException("Book already exist!");
            }
        }

    }
}
