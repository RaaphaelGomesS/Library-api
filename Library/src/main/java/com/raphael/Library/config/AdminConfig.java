package com.raphael.Library.config;

import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class AdminConfig implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;

    private AssociateRepository associateRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        associateRepository.findByUsername("admin").ifPresentOrElse(userAdmin -> System.out.println("Admin já foi criado!"), () -> {

            Associate associate = Associate.builder()
                    .name("admin")
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .role(Associate.RoleIndicator.ADMIN)
                    .build();

            associateRepository.save(associate);
        });
    }
}
