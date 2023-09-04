package com.company.gamestore.repositories;

import com.company.gamestore.models.Fee;
import com.company.gamestore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface FeeRepo extends JpaRepository<Fee, String>{
    Fee findByProductType(String productType);
}
