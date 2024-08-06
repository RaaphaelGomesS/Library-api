package com.raphael.Library.service.book;

import com.raphael.Library.builder.BookBuilder;
import com.raphael.Library.builder.BookResponseDTOBuilder;
import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.dto.GenderFilter;
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

    public Book createBook(BookRequestDTO bookRequestDTO) throws BookException {

        verifyIfAlreadyExist(bookRequestDTO);

        Author author = authorService.createOrGetAuthor(bookRequestDTO);
        Publisher publisher = publisherService.createOrGetPublisher(bookRequestDTO);

        Book book = BookBuilder.from(bookRequestDTO, author, publisher);

        author.getBooks().add(book);
        publisher.getBooks().add(book);

        bookRepository.save(book);
        authorRepository.save(author);
        publisherRepository.save(publisher);

        return book;
    }

    public List<BookResponseDTO> getAllBooksByAuthor(String authorName) throws BookException {

        Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new BookException("Author not found!", HttpStatus.NOT_FOUND));

        if (author.getBooks().isEmpty()) {
            throw new BookException("Author has no books!", HttpStatus.NOT_FOUND);
        }

        return BookResponseDTOBuilder.fromList(author.getBooks());
    }

    public List<BookResponseDTO> getAllBooksByPublisher(String publisherName) throws BookException {

        Publisher publisher = publisherRepository.findByName(publisherName)
                .orElseThrow(() -> new BookException("Author not found!", HttpStatus.NOT_FOUND));

        if (publisher.getBooks().isEmpty()) {
            throw new BookException("Publisher has no books!", HttpStatus.NOT_FOUND);
        }

        return BookResponseDTOBuilder.fromList(publisher.getBooks());
    }

    public List<BookResponseDTO> getBooksByGender(GenderFilter filter) throws BookException {

        List<Book> books = bookRepository.findAll()
                .stream()
                .filter(book -> book.getGender().getNames().contains(filter.getGender()))
                .toList();

        if (books.isEmpty()) {
            throw new BookException("Book already exist!", HttpStatus.NOT_FOUND);
        }

        return books.stream().map(BookResponseDTOBuilder::from).toList();
    }

    private void verifyIfAlreadyExist(BookRequestDTO bookRequestDTO) throws BookException {

        Optional<Book> foundedBook = bookRepository.findByName(bookRequestDTO.getBookName());

        if (foundedBook.isPresent() && foundedBook.get().getAuthor().getName().equalsIgnoreCase(bookRequestDTO.getAuthorName())) {
            throw new BookException("Book already exist!", HttpStatus.CONFLICT);
        }
    }
}
