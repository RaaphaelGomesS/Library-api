package mock.response;

import com.raphael.Library.dto.book.BookResponseDTO;

import java.util.Collections;
import java.util.List;

public class BookResponseDTOMock {

    public static BookResponseDTO toResponse() {

        return BookResponseDTO
                .builder()
                .bookId(1L)
                .name("A MONTANHA MAGICA")
                .gender("ROMANCE")
                .author("THOMAS MANN")
                .publisher("COMPANHIA DAS LETRAS")
                .build();
    }

    public static List<BookResponseDTO> toList() {

        return Collections.singletonList(toResponse());
    }
}
