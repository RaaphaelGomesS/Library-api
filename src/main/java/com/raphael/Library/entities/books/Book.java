package com.raphael.Library.entities.books;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.raphael.Library.entities.Requisition;
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
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderIndicator gender;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToOne(mappedBy = "book")
    private Requisition requisition;
}
