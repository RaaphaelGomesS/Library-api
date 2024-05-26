package com.raphael.Library.service;

import com.raphael.Library.builder.BookBuilder;
import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.entities.books.Publisher;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.AuthorRepository;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    private PublisherRepository publisherRepository;

    private AuthorService authorService;

    private PublisherService publisherService;

    public Book createBook(BookDTO bookDTO) throws BookException {

        verifyIfAlreadyExist(bookDTO);

        Book book = BookBuilder.from(bookDTO);

        Author author = authorService.createAuthorByBook(bookDTO);
        Publisher publisher = publisherService.createPublisherByBook(bookDTO);

        addBooks(author, publisher, book);

        return book;
    }

    private void verifyIfAlreadyExist(BookDTO bookDTO) throws BookException {

        Optional<Book> foundedBook = bookRepository.getBookByTheName(bookDTO.getBookName());

        if (foundedBook.isPresent()) {
            if (foundedBook.get().getAuthor().getName().equalsIgnoreCase(bookDTO.getAuthorName())) {
                throw new BookException("Book already exist!");
            }
        }
    }

    private void addBooks(Author author, Publisher publisher, Book book) {

        book.setAuthor(author);
        book.setPublisher(publisher);

        author.getBooks().add(book);
        publisher.getBooks().add(book);

        bookRepository.save(book);
        authorRepository.save(author);
        publisherRepository.save(publisher);
    }
}
