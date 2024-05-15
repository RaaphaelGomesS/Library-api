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
    @Column(name = "associate_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID associateId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(mappedBy = "associate")
    private List<Requisition> booksInPossession;
}
