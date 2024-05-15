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
public class Requisition {

    @Id
    @Column(name = "requisition_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID requisitionId;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "status")
    private Status status;

    @CreationTimestamp
    private LocalDate retiredDate;

    private LocalDate devolutionDate;
}
