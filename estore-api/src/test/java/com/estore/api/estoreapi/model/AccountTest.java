package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.persistence.AccountFileDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Model-tier")
public class AccountTest {
    // CuT
    Cart mockCart;
    Wishlist mockWishlist;
    PurchaseHistory mockPurchaseHistory;
    ArrayList<Coupon> mockCoupons;
    private Account mockAccount;
    private Account[] mockAccountList;

    @BeforeEach
    public void setup() {
        mockCart = new Cart();
        mockWishlist = new Wishlist();
        mockPurchaseHistory = new PurchaseHistory();
        mockCoupons = new ArrayList<>();
        mockAccount = new Account("username", "displayname", true, mockCart, mockWishlist, mockPurchaseHistory,
                mockCoupons);
        mockAccountList = new Account[1];
    }

    @Test
    public void testGetUserName() {
        // check that username works
        String result = mockAccount.getUserName();

        assertEquals("username", result);
    }

    @Test
    public void testGetDisplayName() {
        // check that display name works
        String result = mockAccount.getDisplayName();

        assertEquals("displayname", result);
    }

    @Test
    public void testIsAdmin() {
        // test that account is admin
        boolean result = mockAccount.getIsAdmin();

        assertTrue(result);
    }

    @Test
    public void testUpdateUserName() {
        // test that updating username works
        mockAccount.updateUserName("newUserName");

        assertEquals("newUserName", mockAccount.getUserName());
    }

    @Test
    public void testUpdateDisplayName() {
        // test that updating display name works
        mockAccount.updateDisplayName("newDisplayName");
        assertEquals("newDisplayName", mockAccount.getDisplayName());
    }

    @Test
    public void testEqualsObject() {
        // test that both accounts are equal
        Account newAccount = new Account(mockAccount.getUserName(), mockAccount.getDisplayName(),
                mockAccount.getIsAdmin(), mockAccount.getCart(), mockAccount.getWishlist(),
                mockAccount.getPurchaseHistory(),
                new ArrayList<Coupon>(Arrays.asList(mockAccount.getUsedCoupons())));
        assertEquals(mockAccount, newAccount);
    }

    @Test
    public void testEqualsObjectFailed() {
        // test that both accounts are not equal
        Object newAccount = new Object();
        assertNotEquals(mockAccount, newAccount);
    }

    @Test
    public void testEqualsUserNameFailed() {
        // test that different usernames means they are not equal
        Account newAccount = new Account("a", mockAccount.getDisplayName(), mockAccount.getIsAdmin(),
                mockAccount.getCart(), mockAccount.getWishlist(), mockAccount.getPurchaseHistory(),
                new ArrayList<Coupon>( Arrays.asList(mockAccount.getUsedCoupons()) ));
        assertNotEquals(mockAccount, newAccount);
    }

    @Test
    public void testEqualsDisplayNameFailed() {
        // test that different displaynames means they are not equal
        Account newAccount = new Account("a", mockAccount.getDisplayName(), mockAccount.getIsAdmin(),
                mockAccount.getCart(), mockAccount.getWishlist(), mockAccount.getPurchaseHistory(),
                new ArrayList<Coupon>( Arrays.asList(mockAccount.getUsedCoupons()) ));
        assertNotEquals(mockAccount, newAccount);
    }

    @Test
    public void testToString() {
        // test that toString formats properly
        String expected = String.format("Account [userName=%s, displayName=%s isAdmin=%b]",
                mockAccount.getUserName(), mockAccount.getDisplayName(), mockAccount.getIsAdmin());
        assertEquals(expected, mockAccount.toString());
    }

    @Test
    public void TestGetCart() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, new Cart(), new Wishlist(),
                new PurchaseHistory(), new ArrayList<Coupon>());

        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, account.getCart().getItems().length);
    }

    @Test
    public void TestGetShoeDoesntExist() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, mockCart, mockWishlist,
                mockPurchaseHistory, mockCoupons);
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        int bad_id = 2;
        // Invoke
        account.getCart().addItemToList(shoe);
        // Analyze
        assertNull(account.getCart().getItem(bad_id));
    }

    @Test
    public void TestAddToCart() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, mockCart, mockWishlist,
                mockPurchaseHistory, mockCoupons);
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);

        // Invoke
        account.getCart().addItemToList(shoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, account.getCart().getItems().length);
        assertEquals(shoe, account.getCart().getItem(shoe.getId()));
    }

    @Test
    public void TestRemoveFromCart() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, mockCart, mockWishlist,
                mockPurchaseHistory, mockCoupons);
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        account.getCart().addItemToList(shoe);

        // Invoke
        account.getCart().removeItemFromList(shoe.getId());

        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, account.getCart().getItems().length);
    }

    @Test
    public void TestRemoveFromCartBadID() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, mockCart, mockWishlist,
                mockPurchaseHistory, mockCoupons);
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        account.getCart().addItemToList(shoe);
        int bad_id = 0;
        // Invoke
        account.getCart().removeItemFromList(bad_id);
        // Analyze
        int actual_length = 0;
        assertNotEquals(actual_length, account.getCart().getItems().length);
    }

    @Test
    public void TestUpdateShoeInCart() {
        // Setup
        Account account = new Account("footware-devs", "Footware Devs", true, mockCart, mockWishlist,
                mockPurchaseHistory, mockCoupons);
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe updatedShoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Orange", "Flat tops", 10.5, 120, 10);

        // Invoke
        account.getCart().addItemToList(shoe);
        Shoe output = account.getCart().updateItemInList(updatedShoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, account.getCart().getItems().length);
        assertEquals(shoe, output);
    }

    @Test
    public void TestApplyCoupon(){
        Coupon coupon = new Coupon("aaa", 0.15);

        mockAccount.applyCoupon(coupon);

        assertEquals(mockAccount.getUsedCoupons().length, 1);
    }
}
