package com.raphael.Library.builder;

import com.raphael.Library.dto.book.BookRequestDTO;
import com.raphael.Library.entities.Book;
import com.raphael.Library.indicator.GenderIndicator;
import com.raphael.Library.utils.StringUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookBuilder {

    public static Book from(BookRequestDTO bookRequestDTO) {

        return Book.builder()
                .name(StringUtils.normalizeName(bookRequestDTO.getBookName()))
                .gender(GenderIndicator.getValueByName(bookRequestDTO.getGender()))
                .author(StringUtils.normalizeName(bookRequestDTO.getAuthorName()))
                .publisher(StringUtils.normalizeName(bookRequestDTO.getPublisherName()))
                .build();
    }
}
