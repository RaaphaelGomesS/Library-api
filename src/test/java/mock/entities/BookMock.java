package mock.entities;

import com.raphael.Library.entities.Book;

public class BookMock {

    public static Book toEntity() {

        return Book.builder()
                .bookId(1L)
                .name("A montanha mágica")
                .author("Thomas Mann")
                .publisher("Companhia das letras")
                .requisition(RequisitionMock.toEntity())
                .build();
    }
}
