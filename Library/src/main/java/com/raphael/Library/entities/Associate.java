package com.raphael.Library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "associate")
public class Associate {

    @Id
    @Column(name = "associate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long associateId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "associate")
    private List<Requisition> booksInPossession;
}
