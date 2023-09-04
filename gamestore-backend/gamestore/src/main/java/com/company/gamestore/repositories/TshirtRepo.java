package com.company.gamestore.repositories;

import com.company.gamestore.models.Tshirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TshirtRepo extends JpaRepository<Tshirt, Integer> {
    List<Tshirt> findByColor(String color);
    List<Tshirt> findBySize(String size);
}
