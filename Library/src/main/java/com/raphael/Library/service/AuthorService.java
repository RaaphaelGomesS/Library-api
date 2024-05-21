package com.raphael.Library.service;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private AuthorRepository repository;



    private Author getAuthor(BookDTO bookDTO) throws BookException {

        Optional<Author> author = repository.findByName(bookDTO.getAuthorName());

        if (author.isEmpty()) {
            throw new BookException("Author not exist!");
        }

        return author.get();
        }
    }
