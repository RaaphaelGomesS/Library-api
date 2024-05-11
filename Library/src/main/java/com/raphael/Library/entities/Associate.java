package com.raphael.Library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "associate")
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @OneToOne
    @Column(name = "address", nullable = false)
    Address address;

    @OneToMany
    @Column(name = "books")
    List<Possession> books;
}
