package com.raphael.Library.service;

import com.raphael.Library.dto.BookDTO;
import com.raphael.Library.entities.books.Publisher;
import com.raphael.Library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublisherService {

    private final PublisherRepository repository;

    public Publisher createPublisherByBook(BookDTO bookDTO) {

        Optional<Publisher> publisher = repository.findByName(bookDTO.getPublisherName());

        if (publisher.isEmpty()) {
            Publisher newPublisher = Publisher.builder().name(bookDTO.getPublisherName()).books(new ArrayList<>()).build();

            repository.save(newPublisher);

            return newPublisher;
        } else {
            return publisher.get();
        }
    }
}
