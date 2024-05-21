package com.raphael.Library.service;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private AuthorRepository repository;

    public Author createAuthorByBook(BookDTO bookDTO) {

        Optional<Author> author = repository.findByName(bookDTO.getAuthorName());

        if (author.isEmpty()) {
            Author newAuthor = Author.builder().name(bookDTO.getAuthorName()).books(new ArrayList<>()).build();

            repository.save(newAuthor);

            return newAuthor;
        } else {
            return author.get();
        }
    }
}
