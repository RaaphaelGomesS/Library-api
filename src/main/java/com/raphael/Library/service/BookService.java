package com.raphael.Library.service;

import com.raphael.Library.builder.BookBuilder;
import com.raphael.Library.builder.BookResponseDTOBuilder;
import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.entities.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.indicator.GenderIndicator;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) throws BookException {

        verifyIfAlreadyExist(bookRequestDTO);

        Book newBook = BookBuilder.from(bookRequestDTO);

        Book book = bookRepository.save(newBook);

        return BookResponseDTOBuilder.from(book);
    }

    public List<BookResponseDTO> getAllBooksByAuthor(String authorName) throws BookException {

        List<Book> books = bookRepository.findByAuthorName(authorName);

        if (books.isEmpty()) {
            throw new BookException(String.format("O autor %s não possui livros cadastrados.", authorName), HttpStatus.BAD_REQUEST);
        }

        return BookResponseDTOBuilder.fromList(books);
    }

    public List<BookResponseDTO> getAllBooksByPublisher(String publisherName) throws BookException {

        List<Book> books = bookRepository.findByPublisherName(publisherName);

        if (books.isEmpty()) {
            throw new BookException(String.format("A editora %s não possui livros cadastrados.", publisherName), HttpStatus.BAD_REQUEST);
        }

        return BookResponseDTOBuilder.fromList(books);
    }

    public List<BookResponseDTO> getBooksByGender(String gender) throws BookException {

        List<Book> books = bookRepository.findAll()
                .stream()
                .filter(book -> book.getGender().equals(GenderIndicator.getValueByName(gender)))
                .toList();

        if (books.isEmpty()) {
            throw new BookException("Nenhum livro com esse genero foi encontrada.", HttpStatus.NOT_FOUND);
        }

        return books.stream().map(BookResponseDTOBuilder::from).toList();
    }

    private void verifyIfAlreadyExist(BookRequestDTO bookRequestDTO) throws BookException {

        Optional<Book> foundedBook = bookRepository.findByBookName(bookRequestDTO.getBookName());

        if (foundedBook.isPresent() && foundedBook.get().getAuthor().equals(StringUtils.normalizeName(bookRequestDTO.getAuthorName()))) {
            throw new BookException("O livro já foi registrado.", HttpStatus.CONFLICT);
        }
    }
}
