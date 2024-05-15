package com.raphael.Library.entities.books;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "department")
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID departmentId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Book> books;
}
