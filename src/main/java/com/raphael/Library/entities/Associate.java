package com.raphael.Library.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "associate")
public class Associate implements UserDetails {

    @Id
    @Column(name = "associateId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long associateId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleIndicator role;

    @OneToMany(mappedBy = "associate")
    @JsonBackReference
    private List<Requisition> booksInPossession;

    @CreationTimestamp
    @Column(name = "creationDate", updatable = false)
    private Instant createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private Instant updateDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role == Associate.RoleIndicator.ADMIN ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleIndicator {
        ADMIN, DEFAULT
    }
}
