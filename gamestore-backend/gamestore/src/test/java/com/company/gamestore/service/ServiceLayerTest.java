package com.company.gamestore.service;

import com.company.gamestore.models.*;
import com.company.gamestore.repositories.*;
import com.company.gamestore.viewModel.InvoiceViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.ast.OpAnd;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
class ServiceLayerTest {

    ServiceLayer serviceLayer;

    ConsoleRepo consoleRepository;

    GameRepo gameRepository;

    InvoiceRepo invoiceRepository;

    TshirtRepo tshirtRepository;

    TaxRepo taxRepository;

    FeeRepo feeRepository;

    public void setupTax(){
        taxRepository = mock(TaxRepo.class);
        Tax tax = new Tax();
        tax.setState("CA");
        tax.setRate(new BigDecimal("0.06"));
        doReturn(tax).when(taxRepository).save(tax);
        doReturn(Optional.of(tax)).when(taxRepository).findById("CA");
    }


    //Before
    @BeforeEach
    public void setUp(){
//        clearRepos();
        setUpConsoleRepository();
        setUpGameRepository();
        setUpTshirtRepository();
        setUpInvoiceRepo();
        setupTax();

        serviceLayer = new ServiceLayer( invoiceRepository, consoleRepository, tshirtRepository, taxRepository, gameRepository, feeRepository);
    }

//    public void clearRepos(){
//        consoleRepository.deleteAll();
//        gameRepository.deleteAll();
//        tshirtRepository.deleteAll();
//        invoiceRepository.deleteAll();
//    }


    //Helper/Builders

    public void setUpTshirtRepository(){
        tshirtRepository = mock(TshirtRepo.class);
        //Tshirt that's been added to repo
        Tshirt tshirt = new Tshirt();
        tshirt.setId(1);
        tshirt.setSize("medium");
        tshirt.setQuantity(5);
        tshirt.setPrice(new BigDecimal("5"));
        tshirt.setColor("red");
        tshirt.setDescription("A cool red shirt");

        //Tshirt that has yet to be added to repo
        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("medium");
        tshirt1.setQuantity(5);
        tshirt1.setPrice(new BigDecimal("5"));
        tshirt1.setColor("red");
        tshirt1.setDescription("A cool red shirt");

        //Mimicking repo
        List<Tshirt> tshirtList = new ArrayList<>();

        tshirtList.add(tshirt);

        //Mocking
        doReturn(tshirt).when(tshirtRepository).save(tshirt1); //return album (w id) when album 2 is added to repo
        doReturn(Optional.of(tshirt)).when(tshirtRepository).findById(1); //return album (w id) when get Tshirt w id of 1 is called
        doReturn(tshirtList).when(tshirtRepository).findAll(); //Return tshirtlist (fake repo) when get all tshirts is called

    }


    public void setUpGameRepository(){
        gameRepository = mock(GameRepo.class);

        Game game = new Game();
        game.setDescription("A game");
        game.setGame_id(1);
        game.setPrice(new BigDecimal(3));
        game.setQuantity(2);
        game.setRating("Great!");
        game.setStudio("nVidia");
        game.setTitle("The Best Game");

        Game game1 = new Game();
        game1.setDescription("A game");
        game1.setPrice(new BigDecimal(3));
        game1.setQuantity(2);
        game1.setRating("Great!");
        game1.setStudio("nVidia");
        game1.setTitle("The Best Game");

        List<Game> games = new ArrayList<>();
        games.add(game);

        doReturn(game).when(gameRepository).save(game1); //Return game (w id, like a repo would do) when game is saved
        doReturn(Optional.of(game)).when(gameRepository).findById(1);
        doReturn(games).when(gameRepository).findAll();

    }

    public void setUpConsoleRepository(){
        consoleRepository = mock(ConsoleRepo.class);
        Console console = new Console();
        console.setId(1);
        console.setManufacturer("Timmy");
        console.setPrice(new BigDecimal(100));
        console.setQuantity(2);
        console.setModel("firstOne");
        console.setProcessor("aOne");
        console.setMemoryAmount("tooMuch");

        Console console1 = new Console();
        console1.setManufacturer("Timmy");
        console1.setPrice(new BigDecimal(100));
        console1.setQuantity(2);
        console1.setModel("firstOne");
        console1.setProcessor("aOne");
        console1.setMemoryAmount("tooMuch");

        List<Console> consoles = new ArrayList<>();
        consoles.add(console);

        doReturn(console).when(consoleRepository).save(console1);
        doReturn(Optional.of(console)).when(consoleRepository).findById(1);
        doReturn(consoles).when(consoleRepository).findAll();
    }

    public void setUpInvoiceRepo(){
        invoiceRepository = mock(InvoiceRepo.class);

        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setQuantity(1);
        invoice.setCity("Austin");
        invoice.setState("Texas");
        invoice.setStreet("Largo");
        invoice.setItem_id(5);
        invoice.setItem_type("Game");
        invoice.setName("Tom");
        invoice.setProcessing_fee(new BigDecimal(5));
        invoice.setSubtotal(new BigDecimal(20));
        invoice.setTax(new BigDecimal(2));
        invoice.setTotal(new BigDecimal(27));
        invoice.setUnit_price(new BigDecimal(25));
        invoice.setZipcode("78706");

        Invoice invoice1 = new Invoice();
        invoice1.setId(1);
        invoice1.setQuantity(1);
        invoice1.setCity("Austin");
        invoice1.setState("Texas");
        invoice1.setStreet("Largo");
        invoice1.setItem_id(5);
        invoice1.setItem_type("Game");
        invoice1.setName("Tom");
        invoice1.setProcessing_fee(new BigDecimal(5));
        invoice1.setSubtotal(new BigDecimal(20));
        invoice1.setTax(new BigDecimal(2));
        invoice1.setTotal(new BigDecimal(27));
        invoice1.setUnit_price(new BigDecimal(25));
        invoice1.setZipcode("78706");
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        doReturn(invoice).when(invoiceRepository).save(invoice1);
        doReturn(Optional.of(invoice)).when(invoiceRepository).findById(1);
        doReturn(invoices).when(invoiceRepository).findAll();
    }


    @Test
    void shouldSaveConsole() throws Exception {
        Console expected = new Console();
        expected.setId(1);
        expected.setManufacturer("Timmy");
        expected.setPrice(new BigDecimal(100));
        expected.setQuantity(2);
        expected.setModel("firstOne");
        expected.setProcessor("aOne");
        expected.setMemoryAmount("tooMuch");

        Console c = new Console();
        c.setManufacturer("Timmy");
        c.setPrice(new BigDecimal(100));
        c.setQuantity(2);
        c.setModel("firstOne");
        c.setProcessor("aOne");
        c.setMemoryAmount("tooMuch");

        c = serviceLayer.addConsole(c);
        assertEquals(expected, c);

    }

    @Test
    public void shouldSaveTshirt(){
         Tshirt tshirtExpected = new Tshirt();
         tshirtExpected.setColor("red");
         tshirtExpected.setPrice(new BigDecimal(5));
         tshirtExpected.setId(1);
         tshirtExpected.setQuantity(5);
         tshirtExpected.setSize("medium");
         tshirtExpected.setDescription("A cool red shirt");

         Tshirt tshirt = new Tshirt();
         tshirt.setColor("red");
         tshirt.setPrice(new BigDecimal(5));
         tshirt.setQuantity(5);
         tshirt.setSize("medium");
         tshirt.setDescription("A cool red shirt");

         tshirt = serviceLayer.addTshirt(tshirt);

         assertEquals(tshirtExpected, tshirt);
    }

    @Test
    public void shouldSaveGame(){
        Game gameExpected = new Game();
        gameExpected.setDescription("A game");
        gameExpected.setGame_id(1);
        gameExpected.setPrice(new BigDecimal(3));
        gameExpected.setQuantity(2);
        gameExpected.setRating("Great!");
        gameExpected.setStudio("nVidia");
        gameExpected.setTitle("The Best Game");

        Game game1 = new Game();
        game1.setDescription("A game");
        game1.setPrice(new BigDecimal(3));
        game1.setQuantity(2);
        game1.setRating("Great!");
        game1.setStudio("nVidia");
        game1.setTitle("The Best Game");

        game1 = serviceLayer.addGame(game1);

        assertEquals(gameExpected, game1);

    }








}