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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    private final AuthorService authorService;

    private final PublisherService publisherService;

    public Book createBook(BookDTO bookDTO) throws BookException {

        verifyIfAlreadyExist(bookDTO);

        Book book = BookBuilder.from(bookDTO);

        Author author = authorService.createAuthorByBook(bookDTO);
        Publisher publisher = publisherService.createPublisherByBook(bookDTO);

        addBooks(author, publisher, book);

        return book;
    }

    public List<Book> getAllBooksByAuthor(String authorName) throws BookException {

        Optional<Author> author = authorRepository.findByName(authorName);

        if (author.isEmpty()) {
            throw new BookException("Author not found!", HttpStatus.NOT_FOUND);
        }

        List<Book> books = author.get().getBooks();

        if (books.isEmpty()) {
            throw new BookException("Author has no books!", HttpStatus.NOT_FOUND);
        }

        return books;
    }

    public List<Book> getAllBooksByPublisher(String publisherName) throws BookException {

        Optional<Publisher> publisher = publisherRepository.findByName(publisherName);

        if (publisher.isEmpty()) {
            throw new BookException("Publisher not found!", HttpStatus.NOT_FOUND);
        }

        List<Book> books = publisher.get().getBooks();

        if (books.isEmpty()) {
            throw new BookException("Publisher has no books!", HttpStatus.NOT_FOUND);
        }

        return books;
    }

    private void verifyIfAlreadyExist(BookDTO bookDTO) throws BookException {

        Optional<Book> foundedBook = bookRepository.findByName(bookDTO.getBookName());

        if (foundedBook.isPresent()) {
            if (foundedBook.get().getAuthor().getName().equalsIgnoreCase(bookDTO.getAuthorName())) {
                throw new BookException("Book already exist!", HttpStatus.CONFLICT);
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

    public Book getBookById(long bookId) throws BookException {

        Optional<Book> foundedBook = bookRepository.findById(bookId);

        if (foundedBook.isEmpty()) {
            throw new BookException("Não foi encontrado um livro com esse ID!", HttpStatus.NOT_FOUND);
        }

        return foundedBook.get();
    }
}
