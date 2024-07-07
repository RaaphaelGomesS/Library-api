package com.raphael.Library.service;

import com.raphael.Library.dto.BookRequestDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public Author createOrGetAuthor(BookRequestDTO bookRequestDTO) {

        Optional<Author> author = repository.findByName(bookRequestDTO.getAuthorName());

        if (author.isEmpty()) {
            Author newAuthor = Author
                    .builder()
                    .name(bookRequestDTO.getAuthorName())
                    .books(new ArrayList<>())
                    .build();

            repository.save(newAuthor);

            return newAuthor;
        } else {
            return author.get();
        }
    }
}
