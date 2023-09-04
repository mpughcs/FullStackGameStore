package com.company.gamestore.repositories;

import com.company.gamestore.models.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TaxRepo extends JpaRepository<Tax, String> {
    Tax findByState(String state);

}
