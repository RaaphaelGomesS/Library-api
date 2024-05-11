package com.raphael.Library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "address")
public class Address {

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
