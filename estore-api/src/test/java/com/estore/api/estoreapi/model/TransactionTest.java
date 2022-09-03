package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.persistence.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TransactionTest {
    // private Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
    // private Shoe[] shoes = new Shoe[1];
    // shoes[0] = shoe;
    // Transaction purchase = new Transaction(120, shoes);

    @Test
    public void TestCtor(){
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction purchase = new Transaction(120, shoes);

        assertEquals(purchase.getPrice(), 120);
        assertEquals(purchase.getShoes().length, shoes.length);
        assertNotEquals(purchase.getId(), "5432");
        assertNotEquals(purchase.hashCode(), 5432);
        assertNotEquals(purchase.toString(), "5432");
    }

    @Test
    public void TestEquals(){
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction purchase = new Transaction(120, shoes);
        Transaction purchase2 = new Transaction(120, shoes);
        assertNotEquals(purchase, purchase2);
        Object obj = new Object();
        assertNotEquals(purchase, obj);
    }

    @Test
    public void TestUpdate(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction newSale = new Transaction(10.00, shoes);
        Transaction purchase = new Transaction(120, shoes);

        // Invoke
        purchase.updateTransaction(newSale);

        // Analyze
        assertEquals(newSale.getPrice(), purchase.getPrice());
        assertEquals(newSale.getShoes().length, purchase.getShoes().length);

    }
}
