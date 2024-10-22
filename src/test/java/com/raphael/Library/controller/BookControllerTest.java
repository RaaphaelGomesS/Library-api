package com.raphael.Library.controller;

import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.entities.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.service.BookService;
import mock.entities.BookMock;
import mock.request.BookRequestDTOMock;
import mock.response.BookResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private BookResponseDTO bookResponseDTO;

    private BookRequestDTO bookRequestDTO;

    private List<Book> books;

    private List<BookResponseDTO> bookResponseDTOS;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        books = BookMock.toList();
        bookRequestDTO = BookRequestDTOMock.toRequest();
        bookResponseDTOS = BookResponseDTOMock.toList();
        bookResponseDTO = BookResponseDTOMock.toResponse();
    }

    @Test
    void shouldCreateBook() {
        assertDoesNotThrow(() -> {

            ResponseEntity<BookResponseDTO> responseEntity = ResponseEntity.ok(bookResponseDTO);

            when(bookService.createBook(any())).thenReturn(bookResponseDTO);

            ResponseEntity<BookResponseDTO> result = bookController.createBook(bookRequestDTO);

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldGetAllBooks() {
        assertDoesNotThrow(() -> {

            when(bookRepository.findAll()).thenReturn(books);

            ResponseEntity<List<BookResponseDTO>> result = bookController.getAllBooks();

            assertNotNull(result);

        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindAnyBook() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(bookRepository.findAll()).thenReturn(Collections.emptyList());

            bookController.getAllBooks();

        });

        Exception expectedException = new BookException("Não possui nenhum livro registrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof BookException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetAllBooksByGender() {
        assertDoesNotThrow(() -> {

            ResponseEntity<List<BookResponseDTO>> responseEntity = ResponseEntity.ok(bookResponseDTOS);

            when(bookService.getBooksByGender(any())).thenReturn(bookResponseDTOS);

            ResponseEntity<List<BookResponseDTO>> result = bookController.getAllBooksByGender("filosofia");

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldGetAllBooksFromAuthor() {
        assertDoesNotThrow(() -> {

            ResponseEntity<List<BookResponseDTO>> responseEntity = ResponseEntity.ok(bookResponseDTOS);

            when(bookService.getAllBooksByAuthor(any())).thenReturn(bookResponseDTOS);

            ResponseEntity<List<BookResponseDTO>> result = bookController.getAllBooksFromAuthor("Thomas Mann");

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldGetAllBooksFromPublisher() {
        assertDoesNotThrow(() -> {

            ResponseEntity<List<BookResponseDTO>> responseEntity = ResponseEntity.ok(bookResponseDTOS);

            when(bookService.getAllBooksByPublisher(any())).thenReturn(bookResponseDTOS);

            ResponseEntity<List<BookResponseDTO>> result = bookController.getAllBooksFromPublisher("Editora 34");

            assertEquals(responseEntity, result);

        });
    }
}
