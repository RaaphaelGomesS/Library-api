package com.raphael.Library.service;

import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.entities.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import mock.entities.BookMock;
import mock.request.BookRequestDTOMock;
import mock.response.BookResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    private List<Book> books;

    private BookRequestDTO bookRequestDTO;

    private BookResponseDTO bookResponseDTO;

    private List<BookResponseDTO> bookResponseDTOS;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        books = BookMock.toList();
        book = BookMock.toEntity();
        bookRequestDTO = BookRequestDTOMock.toRequest();
        bookResponseDTOS = BookResponseDTOMock.toList();
        bookResponseDTO = BookResponseDTOMock.toResponse();
    }

    @Test
    void shouldCreateBook() {
        assertDoesNotThrow(() -> {

            when(bookRepository.findByBookName(any())).thenReturn(Optional.empty());
            when(bookRepository.save(any())).thenReturn(book);

            BookResponseDTO result = bookService.createBook(bookRequestDTO);

            verify(bookRepository, times(1)).save(any());

            assertEquals(bookResponseDTO.getBookId(), result.getBookId());
            assertEquals(bookResponseDTO.getName(), result.getName());
            assertEquals(bookResponseDTO.getAuthor(), result.getAuthor());
            assertEquals(bookResponseDTO.getPublisher(), result.getPublisher());
            assertEquals(bookResponseDTO.getGender(), result.getGender());
        });
    }

    @Test
    void shouldThrowExceptionWhenBookIsAlreadyRegistered() {
        Exception actualException = assertThrows(Exception.class, () -> {

            bookRequestDTO.setAuthorName(book.getAuthor());

            when(bookRepository.findByBookName(any())).thenReturn(Optional.of(book));

            bookService.createBook(bookRequestDTO);

        });

        Exception expectedException = new BookException("O livro já foi registrado.", HttpStatus.CONFLICT);

        assertInstanceOf(BookException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetAllBooksByAuthor() {
        assertDoesNotThrow(() -> {

            when(bookRepository.findByAuthorName(any())).thenReturn(books);

            List<BookResponseDTO> result = bookService.getAllBooksByAuthor("THOMAS MANN");

            assertTrue(bookResponseDTOS.stream().allMatch(b -> result.stream().allMatch(r -> r.getBookId().equals(b.getBookId()))));
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindBookByAuthorName() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(bookRepository.findByAuthorName(any())).thenReturn(Collections.emptyList());

            bookService.getAllBooksByAuthor("TEST");

        });

        Exception expectedException = new BookException(String.format("O autor %s não possui livros cadastrados.", "TEST"), HttpStatus.BAD_REQUEST);

        assertInstanceOf(BookException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetAllBooksByPublisher() {
        assertDoesNotThrow(() -> {

            when(bookRepository.findByPublisherName(any())).thenReturn(books);

            List<BookResponseDTO> result = bookService.getAllBooksByPublisher("COMPANHIA DAS LETRAS");

            assertTrue(bookResponseDTOS.stream().allMatch(b -> result.stream().allMatch(r -> r.getBookId().equals(b.getBookId()))));
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindBookByPublisherName() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(bookRepository.findByPublisherName(any())).thenReturn(Collections.emptyList());

            bookService.getAllBooksByPublisher("TEST");

        });

        Exception expectedException = new BookException(String.format("A editora %s não possui livros cadastrados.", "TEST"), HttpStatus.BAD_REQUEST);

        assertInstanceOf(BookException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetAllBooksByGender() {
        assertDoesNotThrow(() -> {

            when(bookRepository.findAll()).thenReturn(books);

            List<BookResponseDTO> result = bookService.getBooksByGender("romance");

            assertTrue(bookResponseDTOS.stream().allMatch(b -> result.stream().allMatch(r -> r.getBookId().equals(b.getBookId()))));
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindBookByGender() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(bookRepository.findAll()).thenReturn(Collections.emptyList());

            bookService.getBooksByGender("TEST");

        });

        Exception expectedException = new BookException("Nenhum livro com esse genero foi encontrada.", HttpStatus.BAD_REQUEST);

        assertInstanceOf(BookException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}
