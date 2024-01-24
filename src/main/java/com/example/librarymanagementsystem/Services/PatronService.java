package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Entities.Patron;
import com.example.librarymanagementsystem.Entities.User;
import com.example.librarymanagementsystem.Models.Role;
import com.example.librarymanagementsystem.Repositories.PatronRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable("patrons")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patron not found with id: " + id));
    }

    @Transactional
    public Patron addPatron(Patron patron) {
        if (patron.getId() != null) {
            throw new IllegalArgumentException("New patron should not have an ID.");
        }
        User admin = User
                .builder()
                .firstName(patron.getName())
                .email(patron.getContactInformation())
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_USER)
                .build();

        userService.save(admin);
        return patronRepository.save(patron);
    }

    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        Patron existingPatron = getPatronById(id);
        existingPatron.setName(updatedPatron.getName());
        existingPatron.setContactInformation(updatedPatron.getContactInformation());
        return patronRepository.save(existingPatron);
    }

    @Transactional
    public void deletePatron(Long id) {
        Patron existingPatron = getPatronById(id);
        patronRepository.delete(existingPatron);
    }
}
