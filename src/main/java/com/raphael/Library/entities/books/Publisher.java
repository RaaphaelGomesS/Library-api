package com.raphael.Library.entities.books;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
