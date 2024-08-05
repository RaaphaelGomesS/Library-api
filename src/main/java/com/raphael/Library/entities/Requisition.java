package com.raphael.Library.entities;

import com.raphael.Library.entities.books.Book;
import com.raphael.Library.indicator.StatusIndicator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "requisition")
public class Requisition {

    @Id
    @Column(name = "requisition_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requisitionId;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "status")
    private StatusIndicator statusIndicator;

    @CreationTimestamp
    private LocalDate retiredDate;

    private LocalDate updateDate;

    private LocalDate devolutionDate;
}
