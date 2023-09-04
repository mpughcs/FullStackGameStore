package com.company.gamestore.repositories;

import com.company.gamestore.models.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvoiceRepoTest {

    @Autowired
    private InvoiceRepo repo;

    Invoice testInvoice = new Invoice("John Doe", "123 Main St", "Springfield", "IL", "12345", "Console", 1, 1,
            new BigDecimal("59.99"),
            new BigDecimal("59.99"),
            new BigDecimal("3.60"),
            new BigDecimal("5.00"),
            new BigDecimal("68.59"));


    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void shouldAddInvoice() {
        testInvoice = repo.save(testInvoice);
        Optional<Invoice> expected = repo.findById(testInvoice.getId());
        assertEquals(expected.get(), testInvoice);
    }

    @Test
    void shouldFindInvoices() {
        testInvoice = repo.save(testInvoice);
        List<Invoice> expected = new ArrayList<>();
        expected.add(testInvoice);
        assertEquals(expected, repo.findAll());
    }

    @Test
    void shouldFindInvoiceById() {
        testInvoice = repo.save(testInvoice);
        Optional<Invoice> expected = repo.findById(testInvoice.getId());
        assertEquals(expected.get(), testInvoice);
    }

    @Test
    void shouldUpdateInvoice() {
        Invoice updated = new Invoice("John Doe Updated", "123 Main St", "Springfield", "IL", "12345", "Console", 1, 1,
                new BigDecimal("59.99"),
                new BigDecimal("59.99"),
                new BigDecimal("3.60"),
                new BigDecimal("5.00"),
                new BigDecimal("68.59"));

        testInvoice = repo.save(testInvoice);

        updated.setId(testInvoice.getId());
        repo.save(updated);
        assertEquals(updated, repo.findById(testInvoice.getId()).get());
    }

    @Test
    void shouldDeleteInvoice() {
        testInvoice = repo.save(testInvoice);
        Optional<Invoice> optionalInvoice;
        repo.delete(testInvoice);

        optionalInvoice = repo.findById(testInvoice.getId());
        assertFalse(optionalInvoice.isPresent(), "Invoice should be deleted");
    }

    @Test
    void shouldGetInvoiceByName() {
        testInvoice = repo.save(testInvoice);
        List<Invoice> namedInvoices = new ArrayList<>();
        namedInvoices.add(testInvoice);
        assertEquals(namedInvoices, repo.findByName("John Doe"));
    }
}
