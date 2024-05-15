package com.raphael.Library.entities.books;

import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "publisher")
public class Publisher {

    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID publisherId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}