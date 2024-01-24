package com.example.librarymanagementsystem.Configs.SecurityConfigs;

import com.example.librarymanagementsystem.Entities.User;
import com.example.librarymanagementsystem.Models.Role;
import com.example.librarymanagementsystem.Repositories.UserRepository;
import com.example.librarymanagementsystem.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            User admin = User
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userService.save(admin);
            log.info("created ADMIN user - {}", admin);
        }
    }

}