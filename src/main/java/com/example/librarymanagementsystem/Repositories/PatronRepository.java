package com.example.librarymanagementsystem.Repositories;

import com.example.librarymanagementsystem.Entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}