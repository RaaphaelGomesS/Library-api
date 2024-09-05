package com.raphael.Library.entities;

import com.raphael.Library.indicator.GenderIndicator;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "book")
public class Book {

    @Id
    @Column(name = "bookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderIndicator gender;

    @Column(name = "authorName", nullable = false)
    private String author;

    @Column(name = "publisherName", nullable = false)
    private String publisher;

    @OneToOne(mappedBy = "book")
    private Requisition requisition;
}
