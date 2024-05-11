package com.raphael.Library.entities;

import com.raphael.Library.entities.books.Book;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "possession")
public class Possession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID requisitionId;

    @OneToOne
    private Book book;

    private Status status;

    @CreationTimestamp
    private LocalDate retiredDate;

    //1 mouth
    private LocalDate devolutionDate;
}
