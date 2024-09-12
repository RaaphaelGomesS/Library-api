package mock.entities;

import com.raphael.Library.entities.Book;
import com.raphael.Library.indicator.GenderIndicator;

import java.util.Collections;
import java.util.List;

public class BookMock {

    public static Book toEntity() {

        return Book.builder()
                .bookId(1L)
                .name("A MONTANHA MAGICA")
                .author("THOMAS MANN")
                .publisher("COMPANHIA DAS LETRAS")
                .requisition(RequisitionMock.toEntity())
                .gender(GenderIndicator.ROMANCE)
                .build();
    }

    public static List<Book> toList() {

        return Collections.singletonList(toEntity());

    }
}
