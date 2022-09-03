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

@Tag("Model-tier")
public class PurchaseHistoryTest {
    private PurchaseHistory saleHistory = new PurchaseHistory();
    Shoe[] shoes;
    Transaction purchase;
    
    @BeforeEach
    public void setup(){
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        shoes = new Shoe[1];
        shoes[0] = shoe;
        purchase = new Transaction(120, shoes);
    }

    @Test
    public void TestGetItem() {
        // Setup
        saleHistory.addItemToList(purchase);

        // Invoke
        Transaction result = saleHistory.getItem(purchase.getId().hashCode());

        // Analyze
        assertEquals(purchase, result);
    }
    
    @Test
    public void TestGetItems() {
        // Setup
        saleHistory.addItemToList(purchase);

        // Invoke
        Transaction[] result = saleHistory.getItems();

        // Analyze
        assertEquals(1, result.length);
    }

    @Test
    public void TestGetItemFail() {
        // Setup
        saleHistory.addItemToList(purchase);

        // Invoke
        Transaction result = saleHistory.getItem(0);

        // Analyze
        assertNull(result);
    }

    @Test
    public void TestAddItemFail() {
        // Setup
        saleHistory.addItemToList(purchase);
        saleHistory.addItemToList(purchase);

        // Invoke
        Transaction[] result = saleHistory.getItems();

        // Analyze
        assertEquals(1, result.length);
    }

    @Test
    public void TestRemoveFromList() {
        // Setup
        saleHistory.addItemToList(purchase);
        
        // Invoke
        saleHistory.removeItemFromList(purchase.getId().hashCode());

        // Analyze
        assertEquals(0, saleHistory.getItems().length);
    }

    @Test
    public void TestRemoveFromListFail() {
        // Setup
        saleHistory.addItemToList(purchase);
        
        // Invoke
        saleHistory.removeItemFromList(1);

        // Analyze
        assertEquals(1, saleHistory.getItems().length);
    }

    @Test
    public void TestRemoveAllFromList() {
        // Setup
        saleHistory.addItemToList(purchase);
        
        // Invoke
        saleHistory.removeAllFromList();

        // Analyze
        assertEquals(0, saleHistory.getItems().length);
    }

    @Test
    public void TestUpdateItemInList() {
        // Setup
        saleHistory.addItemToList(purchase);
        Transaction newSale = new Transaction(2000, shoes);
        purchase.updateTransaction(newSale);
        
        // Invoke
        saleHistory.updateItemInList(purchase);

        // Analyze
        assertEquals(purchase, saleHistory.getItem(purchase.getId().hashCode()));
    }

    @Test
    public void TestUpdateItemInListFail() {
        // Setup
        saleHistory.addItemToList(purchase);
        Transaction newSale = new Transaction(2000, shoes);
        purchase.updateTransaction(newSale);
        
        // Invoke
        Transaction result = saleHistory.updateItemInList(newSale);

        // Analyze
        assertNull(result);
    }
}
