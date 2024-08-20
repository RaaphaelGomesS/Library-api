package com.raphael.Library.service.book;

import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.entities.books.Publisher;
import com.raphael.Library.repository.PublisherRepository;
import com.raphael.Library.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class PublisherService {

    private final PublisherRepository repository;

    public Publisher createOrGetPublisher(BookRequestDTO bookRequestDTO) {

        String publisherNameNormalized = StringUtils.normalizeName(bookRequestDTO.getPublisherName());

        Publisher publisher = repository.findByName(publisherNameNormalized).orElse(null);

        if (publisher == null) {
            Publisher newPublisher = Publisher
                    .builder()
                    .name(publisherNameNormalized)
                    .books(new ArrayList<>())
                    .build();

            repository.save(newPublisher);

            return newPublisher;
        } else {
            return publisher;
        }
    }
}
