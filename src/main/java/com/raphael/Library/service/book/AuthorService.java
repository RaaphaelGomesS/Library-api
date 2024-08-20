package com.raphael.Library.service.book;

import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.entities.books.Author;
import com.raphael.Library.repository.AuthorRepository;
import com.raphael.Library.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public Author createOrGetAuthor(BookRequestDTO bookRequestDTO) {

        String authorNameNormalized = StringUtils.normalizeName(bookRequestDTO.getAuthorName());

        Author author = repository.findByName(authorNameNormalized).orElse(null);

        if (author == null) {
            Author newAuthor = Author
                    .builder()
                    .name(authorNameNormalized)
                    .books(new ArrayList<>())
                    .build();

            repository.save(newAuthor);

            return newAuthor;
        } else {
            return author;
        }
    }
}
