package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;


import com.estore.api.estoreapi.persistence.*;
import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryDAO mockInventoryDAO;
    private InventoryController inventoryController; // the object we are testing
    private Shoe shoe = new Shoe(1,"Jordan", "Air", 5, "Gray", "Athletic", 6.5, 230.99, 5);
    private final Shoe shoe2 = new Shoe(2,"Yeezy", "Boost", 350, "Gray", "Athletic", 9.5, 230.99, 5);
    private final Shoe shoe3 = new Shoe(3,"Yeezy", "Boost", 700, "Zebra", "Athletic", 9.5, 230.99, 5);
    
    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupInventoryController(){
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    public void testGetShoe() throws IOException {
        // Setup
        // When the same id is passed in, our mock Inventory DAO will return the Shoe object
        when(mockInventoryDAO.getShoe(shoe.getId())).thenReturn(shoe);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.getShoe(shoe.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe, response.getBody());
    }

    @Test
    public void testGetShoeNotFound() throws Exception{
        // Setup
        int shoeId = 99;
        // When the same id is passed in, our mock Inventory DAO will return null, simulating
        // no shoe found
        when(mockInventoryDAO.getShoe(shoeId)).thenReturn(null);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.getShoe(shoeId);

        // Anaylze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetShoeHandleException() throws Exception {
        // Setup
        int shoeId = 99;
        // When getShoe is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getShoe(shoeId);
        
        // Invoke
        ResponseEntity<Shoe> response = inventoryController.getShoe(shoeId);

        // Anaylze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateShoe() throws IOException {
        // Setup
        // when createShoe is called, return true simulating successful
        // creation and save
        when(mockInventoryDAO.createShoe(shoe)).thenReturn(shoe);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.createShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(shoe, response.getBody());
    }

    @Test
    public void testCreateShoeFailed() throws IOException {
        // Setup
        // when createShoe is called, return false simulating failed
        // creation and save
        when(mockInventoryDAO.createShoe(shoe)).thenReturn(null);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.createShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateShoeHandleException() throws Exception {
        // Setup
        
        doThrow(new IOException()).when(mockInventoryDAO).createShoe(shoe);
        
        // Invoke
        ResponseEntity<Shoe> response = inventoryController.createShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test 
    public void testUpdateShoe() throws IOException {
        // when createShoe is called, return true simulating successful
        // creation and save
        when(mockInventoryDAO.updateShoe(shoe)).thenReturn(shoe);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.updateShoe(shoe);
        shoe.replenishStock(5);
        shoe.updatePrice(500);
        response = inventoryController.updateShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe, response.getBody());
    }

    @Test
    public void testUpdateShoeFailed() throws IOException {
        // Setup
        // when updateShoe is called, return false simulating an unsuccessful
        // update and save
        when(mockInventoryDAO.updateShoe(shoe)).thenReturn(null);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.updateShoe(shoe);
        shoe.replenishStock(5);
        shoe.updatePrice(500);
        response = inventoryController.updateShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateShoeHandleException() throws IOException {
        // Setup
        // When updateShoe is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).updateShoe(shoe);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.updateShoe(shoe);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetShoes() throws IOException {
        // Setup
        Shoe[] shoes = new Shoe[2];
        shoes[0] = shoe;
        shoes[1] = shoe2;
        // When getShoes is called return the shoes created above
        when(mockInventoryDAO.getShoes()).thenReturn(shoes);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.getShoes();

        // Analzye
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoes, response.getBody());
    }

    @Test
    public void testGetShoesHandleException() throws IOException {
        // Setup
        // When getShoes is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getShoes();

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.getShoes();

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    @Test
    public void testGetFindShoes() throws IOException {
        // Setup
        String searchString = "Boo";
        Shoe[] shoes = new Shoe[2];
        shoes[0] = shoe2;
        shoes[1] = shoe3;
        // When findShoes is called with the search string, return the two
        /// shoes above
        when(mockInventoryDAO.findShoes(searchString)).thenReturn(shoes);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.findShoes(searchString);

        // Analzye
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoes, response.getBody());
    }

    @Test
    public void testGetFindShoesHandleException() throws IOException {
        // Setup
        String searchString = "wow";
        // When findShoes is called with the search string, return the an 
        //IOException
        doThrow(new IOException()).when(mockInventoryDAO).findShoes(searchString);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.findShoes(searchString);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteShoe() throws IOException {
        // Setup
        int shoeId = 99;
        // when deleteShoe is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteShoe(shoeId)).thenReturn(true);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.deleteShoe(shoeId);

        // Analzye
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteShoeFail() throws IOException {
        // Setup
        int shoeId = 99;
        // when deleteShoe is called return false, simulating an unsuccessful deletion
        when(mockInventoryDAO.deleteShoe(shoeId)).thenReturn(false);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.deleteShoe(shoeId);

        // Analzye
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteShoeHandleException() throws IOException {
        // Setup
        int shoeId = 99;
        // when deleteShoe is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).deleteShoe(shoeId);

        // Invoke
        ResponseEntity<Shoe> response = inventoryController.deleteShoe(shoeId);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test 
    public void testUpdateShoes() throws IOException {
        Shoe[] shoes = new Shoe[2];
        shoes[0] = shoe;
        shoes[1] = shoe2;
        when(mockInventoryDAO.decrementShoes(shoes)).thenReturn(shoes);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.updateShoes(shoes);
        shoe.replenishStock(5);
        shoe.updatePrice(500);
        response = inventoryController.updateShoes(shoes);

        // Analzye
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoes.length, response.getBody().length);
    }

    @Test
    public void testUpdateShoesFailed() throws IOException {
        Shoe[] shoes = new Shoe[2];
        Shoe shoe4 = new Shoe(1,"Yeezy", "Boost", 700, "Zebra", "Athletic", 9.5, 230.99, 0);
        shoes[0] = shoe4;
        shoes[1] = shoe2;
        when(mockInventoryDAO.decrementShoes(shoes)).thenReturn(shoes);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.updateShoes(shoes);
        shoe.replenishStock(5);
        shoe.updatePrice(500);
        response = inventoryController.updateShoes(shoes);

        // Analzye
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(shoes.length, response.getBody().length);
    }

    @Test
    public void testUpdateShoesHandleException() throws IOException {
        // Setup
        // When updateShoes is called on the Mock Inventory DAO, throw an IOException
        Shoe[] shoes = new Shoe[2];
        shoes[0] = shoe;
        shoes[1] = shoe2;
        doThrow(new IOException()).when(mockInventoryDAO).decrementShoes(shoes);

        // Invoke
        ResponseEntity<Shoe[]> response = inventoryController.updateShoes(shoes);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


}
