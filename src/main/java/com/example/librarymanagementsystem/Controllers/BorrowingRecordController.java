package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Configs.APIResponseConfigs.APIResponse;
import com.example.librarymanagementsystem.Entities.Book;
import com.example.librarymanagementsystem.Entities.BorrowingRecord;
import com.example.librarymanagementsystem.Services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/api/borrow/{bookId}/patron/{patronId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.borrowBook(bookId, patronId);
        APIResponse<Void> response = new APIResponse<>(HttpStatus.CREATED.value(), true, null, "Book with id: " + bookId + " has been borrowed by patron with id: " + patronId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/return/{bookId}/patron/{patronId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        APIResponse<Void> response = new APIResponse<>(HttpStatus.OK.value(), true, null, "Book with id: " + bookId + " has been returned by patron with id: " + patronId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<BorrowingRecord>>> getAllBorrowingRecords() {
        List<BorrowingRecord> borrowingRecords = borrowingRecordService.getAllBorrowingRecords();
        APIResponse<List<BorrowingRecord>> response = new APIResponse<>(HttpStatus.OK.value(), true, borrowingRecords, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records/patron/{patronId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<BorrowingRecord>>> getBorrowingRecordsForPatron(@PathVariable Long patronId) {
        List<BorrowingRecord> borrowingRecords = borrowingRecordService.getBorrowingRecordsForPatron(patronId);
        APIResponse<List<BorrowingRecord>> response = new APIResponse<>(HttpStatus.OK.value(), true, borrowingRecords, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<BorrowingRecord>>> getBorrowingRecordsForBook(@PathVariable Long bookId) {
        List<BorrowingRecord> borrowingRecords = borrowingRecordService.getBorrowingRecordsForBook(bookId);
        APIResponse<List<BorrowingRecord>> response = new APIResponse<>(HttpStatus.OK.value(), true, borrowingRecords, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records/patron/{patronId}/returned-last-period/{numOfDays}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<Book>>> getBooksReturnedLastPeriod(@PathVariable Long patronId, @PathVariable Long numOfDays) {
        List<Book> returnedBooks = borrowingRecordService.getBooksReturnedLastPeriod(patronId, numOfDays);
        APIResponse<List<Book>> response = new APIResponse<>(HttpStatus.OK.value(), true, returnedBooks, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records/currently-borrowed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<Book>>> getCurrentlyBorrowedBooks() {
        List<Book> currentlyBorrowedBooks = borrowingRecordService.getCurrentlyBorrowedBooks();
        APIResponse<List<Book>> response = new APIResponse<>(HttpStatus.OK.value(), true, currentlyBorrowedBooks, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/borrowing-records/patron/{patronId}/currently-borrowed-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Integer>> getCurrentlyBorrowedBooksCountForPatron(@PathVariable Long patronId) {
        int borrowedBooksCount = borrowingRecordService.getCurrentlyBorrowedBooksCountForPatron(patronId);
        APIResponse<Integer> response = new APIResponse<>(HttpStatus.OK.value(), true, borrowedBooksCount, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

