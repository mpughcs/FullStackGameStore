package com.company.gamestore.controllers;

import com.company.gamestore.models.Invoice;
import com.company.gamestore.repositories.InvoiceRepo;
import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.viewModel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InvoiceControllerTest {

    @InjectMocks
    InvoiceController controller;

    @Mock
    ServiceLayer serviceLayer;

    @Mock
    InvoiceRepo invoiceRepo;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Invoice sampleInvoice() {
        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 Main St");
        invoice.setCity("Anytown");
        invoice.setState("NY");
        invoice.setZipcode("12345");
        invoice.setItem_type("Game");
        invoice.setId(1);
        return invoice;
    }

    private InvoiceViewModel sampleIVM() {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerName("John");
        ivm.setStreet("123 Main St");
        ivm.setCity("Anytown");
        ivm.setState("NY");
        ivm.setZipcode("12345");
        ivm.setItemType("Game");
        ivm.setItemId(1);
        ivm.setQuantity(2);
        return ivm;
    }

    @Test
    void shouldCreateInvoiceOnPurchase() throws Exception {
        when(serviceLayer.createInvoice(any(InvoiceViewModel.class))).thenReturn(sampleInvoice());
        when(invoiceRepo.save(any(Invoice.class))).thenReturn(sampleInvoice());

        mockMvc.perform(post("/invoices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleIVM())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateInvoice() throws Exception {
        when(invoiceRepo.save(any(Invoice.class))).thenReturn(sampleInvoice());

        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleInvoice())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetInvoiceById() throws Exception {
        when(invoiceRepo.findById(1)).thenReturn(Optional.of(sampleInvoice()));

        mockMvc.perform(get("/invoices/{invoice_id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllInvoices() throws Exception {
        List<Invoice> invoices = Arrays.asList(sampleInvoice(), sampleInvoice());
        when(invoiceRepo.findAll()).thenReturn(invoices);

        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetInvoicesByCustomerName() throws Exception {
        List<Invoice> invoices = Arrays.asList(sampleInvoice());
        when(invoiceRepo.findByName("John")).thenReturn(invoices);

        mockMvc.perform(get("/invoices/customer/John"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldDeleteInvoice() throws Exception {
        doNothing().when(invoiceRepo).deleteById(1);

        mockMvc.perform(delete("/invoices/{invoice_id}", 1))
                .andExpect(status().isNoContent());
    }
}
