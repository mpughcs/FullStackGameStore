//
//package com.company.gamestore.service;
//
//import com.company.gamestore.models.Console;
//import com.company.gamestore.models.Game;
//import com.company.gamestore.models.Invoice;
//import com.company.gamestore.models.Tshirt;
//import com.company.gamestore.repositories.*;
//import com.company.gamestore.viewModel.InvoiceViewModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//
//@Component
//public class ServiceLayer {
//    private InvoiceRepo invoiceRepo;
//    private ConsoleRepo consoleRepo;
//    private TshirtRepo tshirtRepo;
//    private TaxRepo taxRepo;
//    private GameRepo gameRepo;
//    private FeeRepo feeRepo;
//
//    @Autowired
//    public ServiceLayer(
//            InvoiceRepo invoiceRepo,
//            ConsoleRepo consoleRepo,
//            TshirtRepo tshirtRepo,
//            TaxRepo taxRepo,
//            GameRepo gameRepo,
//            FeeRepo feeRepo
//    ) {
//        this.invoiceRepo = invoiceRepo;
//        this.consoleRepo = consoleRepo;
//        this.tshirtRepo = tshirtRepo;
//        this.taxRepo = taxRepo;
//        this.gameRepo = gameRepo;
//        this.feeRepo = feeRepo;
//    }
//
//    @Transactional
//    public Invoice createInvoice(InvoiceViewModel ivm) {
//        System.out.println("entered create invoice");
//        Invoice toReturn = new Invoice();
//        toReturn.setName(ivm.getCustomerName());
//        toReturn.setStreet(ivm.getStreet());
//        toReturn.setCity(ivm.getCity());
//        toReturn.setState(ivm.getState());
//        toReturn.setZipcode(ivm.getZipcode());
//        toReturn.setItem_type(ivm.getItemType());
//        toReturn.setItem_id(ivm.getItemId());
//        toReturn.setQuantity(ivm.getQuantity());
//
//        String tableName = ivm.getItemType();
//        int itemId = ivm.getItemId();
//        System.out.println("item id: " + itemId);
//        int quantity = ivm.getQuantity();
//        double processingFee = 0;
//        double total = 0;
//        double taxRate=0;
//
//        if (tableName.equals("Game")) {
//            Game game = gameRepo.findById(itemId).orElse(null);
//            BigDecimal unitPrice = BigDecimal.valueOf(game.getPrice().doubleValue());
//            toReturn.setUnit_price(unitPrice);
//
//            if (game.getQuantity() < quantity) {
//                throw new IllegalArgumentException("Not enough games in stock");
//            }
//            game.setQuantity(game.getQuantity() - quantity);
//            gameRepo.save(game);
//
//            total += unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue();
//        }
//        if (tableName.equals("Console")) {
//            Console console = consoleRepo.findById(itemId).orElse(null);
//            BigDecimal unitPrice = BigDecimal.valueOf(console.getPrice().doubleValue());
//            toReturn.setUnit_price(unitPrice);
//
//            if (console.getQuantity() < quantity) {
//                throw new IllegalArgumentException("Not enough consoles in stock");
//            }
//            console.setQuantity(console.getQuantity() - quantity);
//            consoleRepo.save(console);
//            total += unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue();
//
//        }
//        if (tableName.equals("T-Shirt")) {
//            Tshirt tshirt = tshirtRepo.findById(itemId).orElse(null);
//            BigDecimal unitPrice = BigDecimal.valueOf(tshirt.getPrice().doubleValue());
//            toReturn.setUnit_price(unitPrice);
//
//            if (tshirt.getQuantity() < quantity) {
//                throw new IllegalArgumentException("Not enough tshirts in stock");
//            }
//            tshirt.setQuantity(tshirt.getQuantity() - quantity);
//            tshirtRepo.save(tshirt);
//            total += unitPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue();
//
//
//        }
////        set subtotal
//        toReturn.setSubtotal(BigDecimal.valueOf(total));
//
//
////        adding tax
//        System.out.println("total: "+total);
//
//        taxRate = taxRepo.findByState(ivm.getState()).getRate().doubleValue();
//        System.out.println("tax rate: "+taxRate);
////        maintain 2 decimal places, maintain precision
//        total = Math.round(total*(1+taxRate)*100.0)/100.0;
//        System.out.println("total after tax: "+total);
//
////        total += total*(taxRate);
////        System.out.println("total after tax: "+total);
//
////        adding processing fee
//
//        processingFee = feeRepo.findByProductType(tableName).getFee().doubleValue();
//
//
//
//        if (quantity > 10) {
//            processingFee += 15.49;
//        }
//
//        toReturn.setProcessing_fee(BigDecimal.valueOf(processingFee));
//        toReturn.setTax(BigDecimal.valueOf(total*taxRate));
//        total += processingFee;
//        toReturn.setTotal( BigDecimal.valueOf(total));
//
//        return toReturn;
//    }
//}
//
package com.company.gamestore.service;

import com.company.gamestore.models.Console;
import com.company.gamestore.models.Game;
import com.company.gamestore.models.Invoice;
import com.company.gamestore.models.Tshirt;
import com.company.gamestore.repositories.*;
import com.company.gamestore.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Best practice, don't put any logic in controllers, put it in service layer.
@Component
public class ServiceLayer {
    private InvoiceRepo invoiceRepo;
    private ConsoleRepo consoleRepo;
    private TshirtRepo tshirtRepo;
    private TaxRepo taxRepo;
    private GameRepo gameRepo;
    private FeeRepo feeRepo;

    @Autowired
    public ServiceLayer(
            InvoiceRepo invoiceRepo,
            ConsoleRepo consoleRepo,
            TshirtRepo tshirtRepo,
            TaxRepo taxRepo,
            GameRepo gameRepo,
            FeeRepo feeRepo
    ) {
        this.invoiceRepo = invoiceRepo;
        this.consoleRepo = consoleRepo;
        this.tshirtRepo = tshirtRepo;
        this.taxRepo = taxRepo;
        this.gameRepo = gameRepo;
        this.feeRepo = feeRepo;
    }
    //Validate item id, throw 404 (not found)
    @Transactional
    public Invoice createInvoice(InvoiceViewModel ivm) {
        System.out.println("entered create invoice");
        Invoice toReturn = new Invoice();
        String tableName = ivm.getItemType();
        int itemId = ivm.getItemId();
        int quantity = ivm.getQuantity();
//        cast all shared fields to invoice object
        toReturn.setName(ivm.getCustomerName());
        toReturn.setStreet(ivm.getStreet());
        toReturn.setCity(ivm.getCity());
        toReturn.setState(ivm.getState());
        toReturn.setZipcode(ivm.getZipcode());
        toReturn.setItem_type(tableName);
        toReturn.setItem_id(itemId);
        toReturn.setQuantity(quantity);

//        get table name, item id, and quantity from ivm for processing

        BigDecimal processingFee = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal taxRate = BigDecimal.ZERO;

//        Make sure item is in stock, set unit price, and subtract quantity from stock
        if (tableName.equals("Game")) {

            Game game = gameRepo.findById(itemId).orElse(null);
            if(game == null){
                throw new ArrayIndexOutOfBoundsException("Game does not exist");
            }
            BigDecimal unitPrice = BigDecimal.valueOf(game.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);
            if (game.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough games in stock");
            }
            game.setQuantity(game.getQuantity() - quantity);
            gameRepo.save(game);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        if (tableName.equals("Console")) {
            Console console = consoleRepo.findById(itemId).orElse(null);
            if(console == null){
                throw new ArrayIndexOutOfBoundsException("Game does not exist");
            }
            BigDecimal unitPrice = BigDecimal.valueOf(console.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);

            if (console.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough consoles in stock");
            }
            console.setQuantity(console.getQuantity() - quantity);
            consoleRepo.save(console);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        if (tableName.equals("T-Shirt")) {
            Tshirt tshirt = tshirtRepo.findById(itemId).orElse(null);
            if(tshirt == null){
                throw new ArrayIndexOutOfBoundsException("Game does not exist");
            }
            BigDecimal unitPrice = BigDecimal.valueOf(tshirt.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);

            if (tshirt.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough tshirts in stock");
            }
            tshirt.setQuantity(tshirt.getQuantity() - quantity);
            tshirtRepo.save(tshirt);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
// Set subtotal
        toReturn.setSubtotal(total.setScale(2, BigDecimal.ROUND_HALF_EVEN));

// Adding tax
        taxRate = taxRepo.findByState(ivm.getState()).getRate();
        toReturn.setTax(total.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_EVEN));

// add tax to total
        total = total.multiply(BigDecimal.ONE.add(taxRate));

// Adding processing fee
        processingFee = feeRepo.findByProductType(tableName).getFee();

        if (quantity > 10) {
            processingFee = processingFee.add(BigDecimal.valueOf(15.49));
        }
        toReturn.setProcessing_fee(processingFee.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        total = total.add(processingFee);

        toReturn.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        return toReturn;
    }

    public List<Invoice> getAllInvoices(){
        return invoiceRepo.findAll();
    }

    public Invoice getInvoiceById(int id){
        Optional<Invoice> invoice = invoiceRepo.findById(id);
        return invoice.orElse(null);
    }
    public List<Invoice> getInvoiceByCustomer(String name){
        return invoiceRepo.findByName(name);
    }

    public void deleteInvoiceById(int id){
        invoiceRepo.deleteById(id);
    }


//Invoice Logic Above


//    Console logic

    public Console addConsole(Console toAdd){
        return consoleRepo.save(toAdd);
    }
    public Console findConsoleById(int id) {
        Optional<Console> console = consoleRepo.findById(id);
        return console.isPresent() ? console.get() : null;
    }

    public List<Console> findAllConsoles() {

        return consoleRepo.findAll();
    }

    public void updateConsole(Console console) {

        consoleRepo.save(console);
    }

    public void removeConsole(int id) {

        consoleRepo.deleteById(id);
    }
    public List<Console> findConsoleByManufacturer( String name){
        List<Console> toReturn = new ArrayList<>();
        for(Console console : consoleRepo.findAll()){
            if (console.getManufacturer().equals(name)){
                toReturn.add(console);
            }
        }
        return toReturn;
    }

    //Tshirt Logic

    public Tshirt addTshirt(Tshirt tshirt){
        return tshirtRepo.save(tshirt);
    }

    public Tshirt getTshirtById(int id){
        Optional<Tshirt> tshirt = tshirtRepo.findById(id);
        return tshirt.orElse(null); //If Tshirt exists, return it, or return null
    }
    //Return all, even if that all is null
    public List<Tshirt> getTshirts(){
        return tshirtRepo.findAll();
    }

    public void updateTshirt(Tshirt tshirt){
        tshirtRepo.save(tshirt);
    }

    public void deleteTshirt(int id){
        tshirtRepo.deleteById(id);
    }
    //Return the entire list since if none exist it just won't return anything
    public List<Tshirt> getTshirtByColor(String color){
       return tshirtRepo.findByColor(color);
    }
    //Same as above
    public List<Tshirt> getTshirtBySize(String size){
        return tshirtRepo.findBySize(size);
    }

//    Game Logic
    public Game addGame(Game game){
        return gameRepo.save(game);
    }

    public Game getGameById(int id){
        Optional<Game> game = gameRepo.findById(id);
        return game.orElse(null); //If Game exists, return it, or return null
    }
    //Return all, even if that all is null
    public List<Game> getGames(){
        return gameRepo.findAll();
    }

    public void updateGame(Game game){
        gameRepo.save(game);
    }

    public void deleteGame(int id){
        gameRepo.deleteById(id);
    }
    //Return the entire list since if none exist it just won't return anything
    public List<Game> getGameByStudio(String studio){
        return gameRepo.findByStudio(studio);
    }
    //Same as above
    public List<Game> getGameByEsrbRating(String esrbRating){
        return gameRepo.findByRating(esrbRating);
    }
    //Same as above
    public List<Game> getGameByTitle(String title){
        return gameRepo.findByTitle(title);
    }











}

