package mock.request;

import com.raphael.Library.dto.book.BookRequestDTO;

public class BookRequestDTOMock {

    public static BookRequestDTO toRequest() {

        return BookRequestDTO
                .builder()
                .bookName("A montanha mágica")
                .gender("romance")
                .authorName("Thomas Mann")
                .publisherName("Companhia das letras")
                .build();
    }
}
