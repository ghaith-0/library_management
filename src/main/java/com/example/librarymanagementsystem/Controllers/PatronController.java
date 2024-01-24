package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Configs.APIResponseConfigs.APIResponse;
import com.example.librarymanagementsystem.Entities.Patron;
import com.example.librarymanagementsystem.Services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<Patron>>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        APIResponse<List<Patron>> response = new APIResponse<>(HttpStatus.OK.value(), true, patrons, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Patron>> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        APIResponse<Patron> response = new APIResponse<>(HttpStatus.OK.value(), true, patron, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Patron>> addPatron(@RequestBody @Valid Patron patron) {
        Patron addedPatron = patronService.addPatron(patron);
        APIResponse<Patron> response = new APIResponse<>(HttpStatus.CREATED.value(), true, addedPatron, "New patron added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Patron>> updatePatron(@PathVariable Long id, @RequestBody @Valid Patron patron) {
        Patron updatedPatron = patronService.updatePatron(id, patron);
        APIResponse<Patron> response = new APIResponse<>(HttpStatus.OK.value(), true, updatedPatron, "Patron with id: " + id + " updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        APIResponse<Void> response = new APIResponse<>(HttpStatus.NO_CONTENT.value(), true, null, "Patron with id: " + id + " deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

