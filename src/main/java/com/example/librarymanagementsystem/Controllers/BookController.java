package com.example.librarymanagementsystem.Controllers;// BookController.java

import com.example.librarymanagementsystem.Configs.APIResponseConfigs.APIResponse;
import com.example.librarymanagementsystem.Entities.Book;
import com.example.librarymanagementsystem.Services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<APIResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        APIResponse<List<Book>> response = new APIResponse<>(HttpStatus.OK.value(), true, books, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        APIResponse<Book> response = new APIResponse<>(HttpStatus.OK.value(), true, book, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Book>> addBook(@RequestBody @Valid Book book) {
        Book addedBook = bookService.addBook(book);
        APIResponse<Book> response = new APIResponse<>(HttpStatus.CREATED.value(), true, addedBook, "New book added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Book>> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        APIResponse<Book> response = new APIResponse<>(HttpStatus.OK.value(), true, updatedBook, "Book with id: " + id + " updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        APIResponse<Void> response = new APIResponse<>(HttpStatus.NO_CONTENT.value(), true, null, "Book with id: " + id + " deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

