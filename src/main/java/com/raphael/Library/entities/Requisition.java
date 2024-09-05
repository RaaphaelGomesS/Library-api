package com.raphael.Library.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "requisitionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requisitionId;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "associateId", referencedColumnName = "associateId", nullable = false)
    private Associate associate;

    @Column(name = "associateId", insertable = false, updatable = false, nullable = false)
    private Long associateId;

    @OneToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false)
    private Book book;

    @Column(name = "bookId", insertable = false, updatable = false, nullable = false)
    private Long bookId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusIndicator statusIndicator;

    @CreationTimestamp
    private LocalDate retiredDate;

    private LocalDate updateDate;

    private LocalDate devolutionDate;
}
