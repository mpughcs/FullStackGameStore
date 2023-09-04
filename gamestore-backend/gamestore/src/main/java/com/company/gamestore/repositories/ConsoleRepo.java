package com.company.gamestore.repositories;

import com.company.gamestore.models.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsoleRepo extends JpaRepository<Console,Integer> {
    List<Console> findByManufacturer(String manufacturer);

}
