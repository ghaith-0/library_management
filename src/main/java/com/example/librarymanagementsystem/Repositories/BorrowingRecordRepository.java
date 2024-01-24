package com.example.librarymanagementsystem.Repositories;

import com.example.librarymanagementsystem.Entities.Book;
import com.example.librarymanagementsystem.Entities.BorrowingRecord;
import com.example.librarymanagementsystem.Entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);

    List<BorrowingRecord> findByBook(Book bookId);

    List<BorrowingRecord> findByPatron(Patron patronId);

    List<BorrowingRecord> findByPatronAndReturnDateBetween(Patron patron, Date startDate, Date endDate);

    List<BorrowingRecord> findByReturnDateIsNull();

    List<BorrowingRecord> findByPatronAndReturnDateIsNull(Patron patron);
}