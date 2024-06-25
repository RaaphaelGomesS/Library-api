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

    @Column(name = "user", nullable = false, unique = true)
    private String user;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleIndicator role;

    @OneToMany(mappedBy = "associate")
    private List<Requisition> booksInPossession;

    public enum RoleIndicator {
        ADMIN, DEFAULT
    }
}
