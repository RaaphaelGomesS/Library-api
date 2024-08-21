package com.raphael.Library.controller;

import com.raphael.Library.builder.BookResponseDTOBuilder;
import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.dto.book.BookResponseDTO;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    @PostMapping("/create")
    @Operation(summary = "Adiciona o livro na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o livro criado."),
            @ApiResponse(responseCode = "403", description = "O livro já foi registrado.")
    })
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO bookRequestDTO) throws BookException {

        Book book = bookService.createBook(bookRequestDTO);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/")
    @Operation(summary = "Busca todos os livros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todos os livros."),
            @ApiResponse(responseCode = "404", description = "Não possui nenhum livro registrado.")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() throws BookException {

        List<BookResponseDTO> books = bookRepository.findAll()
                .stream()
                .map(BookResponseDTOBuilder::from)
                .toList();

        if (books.isEmpty()) {
            throw new BookException("Não possui nenhum livro registrado.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/gender")
    @Operation(summary = "Busca todos os livros pelo genero.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todos os livros de um mesmo genero."),
            @ApiResponse(responseCode = "404", description = "Nenhum livro com esse genero foi encontrada.")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooksByGender(@RequestParam("gender") String gender) throws BookException {

        List<BookResponseDTO> bookResponseDTOS = bookService.getBooksByGender(gender);

        return ResponseEntity.ok(bookResponseDTOS);
    }

    @GetMapping("/author")
    @Operation(summary = "Busca todos os livros pelo nome do autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todos os livros do autor."),
            @ApiResponse(responseCode = "400", description = "Autor não possui livros registrados."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooksFromAuthor(@RequestParam("author") String authorName) throws BookException {

        List<BookResponseDTO> book = bookService.getAllBooksByAuthor(authorName);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/publisher")
    @Operation(summary = "Busca todos os livros de uma editora.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna todos os livros da editora."),
            @ApiResponse(responseCode = "400", description = "Editora não possui livros registrados."),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada.")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooksFromPublisher(@RequestParam("publisher") String publisherName) throws BookException {

        List<BookResponseDTO> book = bookService.getAllBooksByPublisher(publisherName);

        return ResponseEntity.ok(book);
    }
}