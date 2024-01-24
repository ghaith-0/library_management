package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Entities.Book;
import com.example.librarymanagementsystem.Entities.BorrowingRecord;
import com.example.librarymanagementsystem.Entities.Patron;
import com.example.librarymanagementsystem.Repositories.BorrowingRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;
    @Transactional
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);

        // Additional validation checks
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(Date.from(Instant.now()));

        borrowingRecordRepository.save(borrowingRecord);

        // Update book's availability status
        book.setBorrowed(true);
        bookService.updateBook(book.getId(), book);
    }
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        // Retrieve the borrowing record based on bookId and patronId
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found"));

        // Update the return date
        borrowingRecord.setReturnDate(Date.from(Instant.now()));

        borrowingRecordRepository.save(borrowingRecord);

        // Update book's availability status
        Book book = borrowingRecord.getBook();
        book.setBorrowed(false);
        bookService.updateBook(book.getId(), book);
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public List<BorrowingRecord> getBorrowingRecordsForPatron(Long patronId) {
        Patron patron = patronService.getPatronById(patronId);
        return borrowingRecordRepository.findByPatron(patron);
    }

    public List<BorrowingRecord> getBorrowingRecordsForBook(Long bookId) {
        Book book = bookService.getBookById(bookId);
        return borrowingRecordRepository.findByBook(book);
    }

    public List<Book> getBooksReturnedLastPeriod(Long patronId, Long numOfDays) {
        Date startDate = Date.from(Instant.now().minus(numOfDays, ChronoUnit.DAYS));
        Patron patron = patronService.getPatronById(patronId);
        List<BorrowingRecord> returnedRecords = borrowingRecordRepository.findByPatronAndReturnDateBetween(
                patron,
                startDate,
                Date.from(Instant.now())
        );
        return returnedRecords.stream()
                .map(BorrowingRecord::getBook)
                .collect(Collectors.toList());
    }

    public List<Book> getCurrentlyBorrowedBooks() {
        List<BorrowingRecord> currentlyBorrowedRecords = borrowingRecordRepository.findByReturnDateIsNull();
        return currentlyBorrowedRecords.stream()
                .map(BorrowingRecord::getBook)
                .collect(Collectors.toList());
    }

    public int getCurrentlyBorrowedBooksCountForPatron(Long patronId) {
        Patron patron = patronService.getPatronById(patronId);
        List<BorrowingRecord> currentlyBorrowedRecords = borrowingRecordRepository.findByPatronAndReturnDateIsNull(patron);
        return currentlyBorrowedRecords.size();
    }
}

