package com.company.gamestore.repositories;

import com.company.gamestore.models.Tshirt;
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
class TshirtRepoTest {


    @Autowired
    private TshirtRepo tshirtRepo;

    @BeforeEach
    public void setUp() throws Exception{
        tshirtRepo.deleteAll();
    }

    @Test
    public void shouldAddTshirt(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        Optional<Tshirt> tshirt1 =tshirtRepo.findById(tshirt.getId());

        assertEquals(tshirt1.get(), tshirt);
    }

    @Test
    public void shouldGetAllTshirts() throws Exception{
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirt=tshirtRepo.save(tshirt);

        //Set up
        Tshirt tshirt2 = new Tshirt();
        tshirt2.setSize("large");
        tshirt2.setColor("blue");
        tshirt2.setDescription("pretty shirt");
        tshirt2.setPrice(new BigDecimal("200.00"));
        tshirt2.setQuantity(20);

        //Save T-shirt

        tshirt2=tshirtRepo.save(tshirt2);



        //Get all tshirts
        List<Tshirt> tshirtList = tshirtRepo.findAll();


        //Assert
        assertEquals(tshirtList, tshirtRepo.findAll());

    }

    @Test
    public void shouldGetTshirtById(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        Optional<Tshirt> tshirt1 =tshirtRepo.findById(tshirt.getId());

        assertEquals(tshirt1.get(), tshirt);
    }

    @Test
    public void shouldUpdateTshirt(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        //Update
        tshirt.setSize("large");
        tshirtRepo.save(tshirt); //does it by id

        //Assert

        Optional<Tshirt> tshirt1 =tshirtRepo.findById(tshirt.getId());

        assertEquals(tshirt1.get(), tshirt);
    }

    @Test
    public void shouldDeleteTshirt(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        //Delete t-shirt

        tshirtRepo.deleteById(tshirt.getId());

        //Assert

        Optional<Tshirt> tshirt1 = tshirtRepo.findById(tshirt.getId());
        assertFalse(tshirt1.isPresent());
    }

    @Test
    public void shouldGetTshirtByColor(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        //Assert
        List<Tshirt> tshirt1 = tshirtRepo.findByColor("red");

        assertEquals(tshirt1.get(0), tshirt);
    }

    @Test
    public void shouldGetTshirtBySize(){
        //Set up
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("medium");
        tshirt.setColor("red");
        tshirt.setDescription("pretty shirt");
        tshirt.setPrice(new BigDecimal("200.00"));
        tshirt.setQuantity(20);

        //Save T-shirt

        tshirtRepo.save(tshirt);

        //Assert
        List<Tshirt> tshirt1 = tshirtRepo.findBySize("medium");

        assertEquals(tshirt1.get(0), tshirt);

    }


}