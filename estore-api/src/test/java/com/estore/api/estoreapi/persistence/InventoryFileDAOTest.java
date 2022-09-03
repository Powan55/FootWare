package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author Mathew Owusu Jr, Laxmi Poudel
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Shoe[] testShoes;

     // Mock object
    ObjectMapper mockObjectMapper;
    AccountFileDAO accountFileDAO;
    private Account[] mockAccountList;
    private Cart mockCart;
    private Wishlist mockWishlist;
    private PurchaseHistory mockPurchaseHistory;
    private ArrayList<Coupon> mockCoupons;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testShoes = new Shoe[3];
        testShoes[0] = new Shoe(5, "Nike", "Air Force 1", 500, "White", "ATHLETIC", 9.5, 150.0, 5);
        testShoes[1] = new Shoe(6, "Nike", "Air Max 1", 500, "Blue", "ATHLETIC", 10, 190.0, 8);
        testShoes[2] = new Shoe(7, "Nike", "Air Jordan 1", 500, "Red", "ATHLETIC", 11, 210.0, 3);


        mockAccountList = new Account[3];
        mockCart = new Cart();
        mockWishlist = new Wishlist();
        mockPurchaseHistory = new PurchaseHistory();
        mockCoupons = new ArrayList<>();


        mockAccountList[0] = new Account("username", "displayname", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the shoe array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Shoe[].class))
                .thenReturn(testShoes);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetShoes() {
        // Invoke
        Shoe[] shoes = inventoryFileDAO.getShoes();

        // Analyze
        assertEquals(shoes.length, testShoes.length);
        for (int i = 0; i < testShoes.length; ++i)
            assertEquals(shoes[i], testShoes[i]);
    }

    @Test
    public void testFindShoes() {
        // Invoke
        Shoe[] shoes = inventoryFileDAO.findShoes("Air");
        Shoe [] shoes2 = inventoryFileDAO.findShoes(null);

        // Analyze
        assertEquals(shoes.length, 3);
        assertEquals(shoes[0], testShoes[0]);
        assertEquals(shoes[1], testShoes[1]);
        assertEquals(shoes[2], testShoes[2]);

        assertEquals(shoes2.length, 3);

        
    }

    @Test
    public void testGetShoe() {
        // Invoke
        Shoe shoe = inventoryFileDAO.getShoe(5);

        // Analzye
        assertEquals(shoe, testShoes[0]);
    }

    @Test
    public void testDeleteShoe() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteShoe(5),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, true);
        // We check the internal tree map size against the length
        // of the test shoes array - 1 (because of the delete)
        // Because shoes attribute of InventoryFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.inventory.size(), testShoes.length - 1);
    }

    @Test
    public void testCreateShoe() throws IOException {
        // Invoke
        Shoe shoe = new Shoe(57, "fake", "none", 800, "a", "b", 6.5, 94.0, 6);
        Shoe result = assertDoesNotThrow(() -> inventoryFileDAO.createShoe(shoe),
                "Unexpected exception thrown");
        Shoe alreadyexists = assertDoesNotThrow(() -> inventoryFileDAO.createShoe(testShoes[0]),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        assertEquals(shoe, result);
        assertEquals(testShoes[0], alreadyexists);
    }

    @Test
    public void testUpdateShoe() {
        // Setup
        Shoe shoe = new Shoe(5, "Nike", "Air Force 1", 500, "White", "ATHLETIC", 9.5, 150.0, 5);

        // Invoke
        Shoe result = assertDoesNotThrow(() -> inventoryFileDAO.updateShoe(shoe),
                "Unexpected exception thrown");
        Shoe failedShoe = assertDoesNotThrow(() -> inventoryFileDAO.updateShoe(new Shoe(10, "a", "Air b 1", 500, "df", "h", 9.5, 150.0, 5)),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        assertEquals(shoe, result);
        assertNull(failedShoe);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Shoe[].class));

        Shoe shoe = new Shoe(8, "Nike", "Air Huarache", 500, "Gray", "ATHLETIC", 9, 210.0, 3);

        assertThrows(IOException.class,
                () -> inventoryFileDAO.createShoe(shoe),
                "IOException not thrown");
    }

    @Test
    public void testGetShoeNotFound() throws IOException {
        // Invoke
        Shoe shoe = inventoryFileDAO.getShoe(11);

        // Analyze
        assertEquals(shoe, null);
    }

    @Test
    public void testDeleteShoeNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteShoe(11),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(result, false);
        assertEquals(inventoryFileDAO.inventory.size(), testShoes.length);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the CartFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Shoe[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

    @Test
    public void testFindShoe() {
        // Invoke
        Shoe shoe[] = inventoryFileDAO.findShoes("Air Force 1");
        Shoe shoe2[] = inventoryFileDAO.findShoes("White");
        Shoe shoe3[] = inventoryFileDAO.findShoes("Nike");
        Shoe shoe4[] = inventoryFileDAO.findShoes("ATHLETIC");
        
        Shoe shoe5[] = inventoryFileDAO.findShoes("190.0");
        Shoe shoe6[] = inventoryFileDAO.findShoes("500");

        // Analzye
        assertEquals(shoe[0], testShoes[0]);
        assertEquals(shoe2[0], testShoes[0]);
        assertEquals(shoe3.length, testShoes.length);
        assertEquals(shoe4.length, testShoes.length);
        assertEquals(shoe5[0], testShoes[1]);
        assertEquals(shoe6.length, testShoes.length);
    }


    @Test
    public void testDecrementShoes() throws IOException {
        // Invoke
        Shoe shoe[] = inventoryFileDAO.decrementShoes(testShoes);

        //accountFileDAO.addToCart("username", testShoes[0]);
        

        // Analzye
        assertEquals(shoe[0].getQuantity(), testShoes[0].getQuantity());
        
    }

}
