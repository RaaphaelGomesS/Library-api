package com.raphael.Library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "address")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;

    @OneToOne(mappedBy = "address")
    private Associate associate;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "state", nullable = false)
    String state;

    @Column(name = "countryAbv", nullable = false)
    String countryAbv;

    @Column(name = "street", nullable = false)
    String street;

    @Column(name = "zipcode", nullable = false)
    String zipcode;
}
