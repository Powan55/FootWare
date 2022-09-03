package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.persistence.AccountFileDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class AccountControllerTest {
    // CuT
    private AccountController accountController;

    // Mock object
    private AccountFileDAO mockAccountFileDAO;

    // Test objects
    private Cart mockCart = new Cart();
    private Wishlist mockWishlist = new Wishlist();
    private PurchaseHistory mockPurchaseHistory = new PurchaseHistory();
    private ArrayList<Coupon> mockCoupons = new ArrayList<>();
    private final Account account = new Account("username", "displayname", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);
    private Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
    private Account[] mockAccountList = new Account[1];

    @BeforeEach
    public void setup() {
        mockAccountFileDAO = mock(AccountFileDAO.class);
        accountController = new AccountController(mockAccountFileDAO);
    }

    @Test
    public void testGetAccount() throws IOException {
        // checks that response status is OK when given valid username
        when(mockAccountFileDAO.getAccount(anyString())).thenReturn(account);

        ResponseEntity<Account> response = accountController.getAccount(account.getUserName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAccountFailed() throws IOException {
        // checks that response status is NOT_FOUND when given nonexistent username
        when(mockAccountFileDAO.getAccount(anyString())).thenReturn(null);

        ResponseEntity<Account> response = accountController.getAccount(account.getUserName());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAccountError() throws IOException {
        // test that IOException thrown returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).getAccount(anyString());

        ResponseEntity<Account> response = accountController.getAccount(account.getUserName());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAccounts() throws IOException {
        // test that HttpStatus is OK when given valid list of Accounts
        when(mockAccountFileDAO.getAccounts()).thenReturn(mockAccountList);

        ResponseEntity<Account[]> response = accountController.getAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAccountsError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).getAccounts();

        ResponseEntity<Account[]> response = accountController.getAccounts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateAccount() throws IOException {
        // test valid created account returns CREATED
        when(mockAccountFileDAO.createAccount(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateAccountFailed() throws IOException {
        // test that creating an existing account returns CONFLICT
        when(mockAccountFileDAO.createAccount(account)).thenReturn(null);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateAccountError() throws IOException {
        // test that creating invalid account returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).createAccount(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteAccount() throws IOException {
        // test valid deletion returns OK
        when(mockAccountFileDAO.deleteAccount(anyString())).thenReturn(true);

        ResponseEntity<Account> response = accountController.deleteAccount(account.getUserName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAccountFailed() throws IOException {
        // test that invalid deletion returns NOT_FOUND
        when(mockAccountFileDAO.deleteAccount(anyString())).thenReturn(false);

        ResponseEntity<Account> response = accountController.deleteAccount(account.getUserName());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAccountError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).deleteAccount(account.getUserName());

        ResponseEntity<Account> response = accountController.deleteAccount(account.getUserName());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testFindAccounts() throws IOException {
        // test that valid matching returns OK
        when(mockAccountFileDAO.findAccountsByUsername(any())).thenReturn(mockAccountList);

        ResponseEntity<Account[]> response = accountController.findAccountsByUsername(any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAccountsFailed() throws IOException {
        // test that invalid matching returns NOT_FOUND
        when(mockAccountFileDAO.findAccountsByUsername(any())).thenReturn(null);

        ResponseEntity<Account[]> response = accountController.findAccountsByUsername(any());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindAccountsError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).findAccountsByUsername(any());

        ResponseEntity<Account[]> response = accountController.findAccountsByUsername(any());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateAccount() throws IOException {
        // test that valid updating returns OK
        when(mockAccountFileDAO.updateAccount(account.getUserName(), account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.updateAccount(account.getUserName(), account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateAccountFailed() throws IOException {
        // test that invalid updating returns NOT_FOUND
        when(mockAccountFileDAO.updateAccount(account.getUserName(), account)).thenReturn(null);

        ResponseEntity<Account> response = accountController.updateAccount(account.getUserName(), account);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAccountError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        doThrow(new IOException()).when(mockAccountFileDAO).updateAccount(account.getUserName(), account);

        ResponseEntity<Account> response = accountController.updateAccount(account.getUserName(), account);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getCart(anyString())).thenReturn(mockCart.getItems());
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.getCart(anyString());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart.getItems().length ,response.getBody().length);
    }

    @Test
    public void testGetCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).getCart(anyString());
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.getCart(anyString());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.addToCart("testUsername", shoe)).thenReturn(shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(shoe ,response.getBody());
    }

    @Test
    public void testAddToCartBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.addToCart("testUsername", shoe)).thenReturn(null);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddToCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).addToCart("testUsername", shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetFromCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getShoeFromCart("testUsername", shoe.getId())).thenReturn(shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe ,response.getBody());
    }

    @Test
    public void testGetFromCartBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getShoeFromCart("testUsername", shoe.getId())).thenReturn(null);
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetFromCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).getShoeFromCart("testUsername", shoe.getId());
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeFromCart("testUsername", shoe.getId())).thenReturn(true);
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveromCartBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeFromCart("testUsername", shoe.getId())).thenReturn(false);
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetRemoveCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).removeFromCart("testUsername", shoe.getId());
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromCart("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateInCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.updateShoeInCart("testUsername", shoe)).thenReturn(shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.updateShoeInCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe ,response.getBody());
    }

    @Test
    public void testUpdateInCartBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.updateShoeInCart("testUsername", shoe)).thenReturn(null);
        // Invoke
        ResponseEntity<Shoe> response = accountController.updateShoeInCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateInCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).updateShoeInCart("testUsername", shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.updateShoeInCart("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToWishlist() throws IOException { 
        // Setup
        when(mockAccountFileDAO.addToWishlist("testUsername", shoe)).thenReturn(shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToWishlist("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(shoe ,response.getBody());
    }

    @Test
    public void testAddToWishlistBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.addToWishlist("testUsername", shoe)).thenReturn(null);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToWishlist("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testaddToWishlistError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).addToWishlist("testUsername", shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.addToWishlist("testUsername", shoe);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testGetWishlist() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getWishlist(anyString())).thenReturn(mockWishlist.getItems());
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.getWishlist(anyString());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockWishlist.getItems().length ,response.getBody().length);
    }

    @Test
    public void testGetWishlistError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).getWishlist(anyString());
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.getWishlist(anyString());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetFromWishlist() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getShoeFromWishlist("testUsername", shoe.getId())).thenReturn(shoe);
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe ,response.getBody());
    }

    @Test
    public void testGetFromWishlistBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.getShoeFromWishlist("testUsername", shoe.getId())).thenReturn(null);
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetFromWishlistError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).getShoeFromWishlist("testUsername", shoe.getId());
        // Invoke
        ResponseEntity<Shoe> response = accountController.getShoeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromWishlist() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeFromWishlist("testUsername", shoe.getId())).thenReturn(true);
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveromWishlistBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeFromWishlist("testUsername", shoe.getId())).thenReturn(false);
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveromWishlistError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).removeFromWishlist("testUsername", shoe.getId());
        // Invoke
        ResponseEntity<Shoe> response = accountController.removeFromWishlist("testUsername", shoe.getId());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAllRemoveFromCart() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeAllFromCart("testUsername")).thenReturn(true);
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.removeAllFromCart("testUsername");
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAllRemoveromCartBad() throws IOException { 
        // Setup
        when(mockAccountFileDAO.removeAllFromCart("testUsername")).thenReturn(false);
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.removeAllFromCart("testUsername");
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAllRemoveCartError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        doThrow(new IOException()).when(mockAccountFileDAO).removeAllFromCart("testUsername");
        // Invoke
        ResponseEntity<Shoe[]> response = accountController.removeAllFromCart("testUsername");
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToPurchaseHistory() throws IOException { 
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        when(mockAccountFileDAO.addToPurchaseHistory("testUsername", sale)).thenReturn(sale);
        // Invoke
        ResponseEntity<Transaction> response = accountController.addToPurchaseHistory("testUsername", sale);
        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddToPurchaseHistoryBad() throws IOException { 
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        when(mockAccountFileDAO.addToPurchaseHistory("testUsername", sale)).thenReturn(null);
        // Invoke
        ResponseEntity<Transaction> response = accountController.addToPurchaseHistory("testUsername", sale);
        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testAddToPurchaseHistoryError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        doThrow(new IOException()).when(mockAccountFileDAO).addToPurchaseHistory("testUsername", sale);
        // Invoke
        ResponseEntity<Transaction> response = accountController.addToPurchaseHistory("testUsername", sale);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testGetPurchaseHistory() throws IOException { 
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        PurchaseHistory history = new PurchaseHistory();
        history.addItemToList(sale);
        Transaction[] list = history.getItems();
        when(mockAccountFileDAO.getPurchaseHistory("testUsername")).thenReturn(list);
        // Invoke
        ResponseEntity<Transaction[]> response = accountController.getPurchaseHistory("testUsername");
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetPurchaseHistoryError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        PurchaseHistory history = new PurchaseHistory();
        history.addItemToList(sale);
        Transaction[] list = history.getItems();
        doThrow(new IOException()).when(mockAccountFileDAO).getPurchaseHistory("testUsername");
        // Invoke
        ResponseEntity<Transaction[]> response = accountController.getPurchaseHistory("testUsername");
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetTransactionFromPurchaseHistory() throws IOException { 
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        
        PurchaseHistory history = new PurchaseHistory();
        history.addItemToList(sale);
        Transaction[] list = history.getItems();
        when(mockAccountFileDAO.getTransactionFromPurchaseHistory("testUsername", sale.getId().hashCode())).thenReturn(sale);
        // Invoke
        ResponseEntity<Transaction> response = accountController.getTransactionFromPurchaseHistory("testUsername", sale.getId());
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetTransactionFromPurchaseHistoryBad() throws IOException { 
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        Integer saleID = 1;
        PurchaseHistory history = new PurchaseHistory();
        history.addItemToList(sale);
        Transaction[] list = history.getItems();
        when(mockAccountFileDAO.getTransactionFromPurchaseHistory("testUsername", saleID)).thenReturn(null);
        // Invoke
        ResponseEntity<Transaction> response = accountController.getTransactionFromPurchaseHistory("testUsername", saleID.toString());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetTransactionFromPurchaseHistoryError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe[] shoes = new Shoe[1];
        shoes[0] = shoe;
        Transaction sale = new Transaction(100, shoes);
        Integer saleID = sale.getId().hashCode();
        PurchaseHistory history = new PurchaseHistory();
        history.addItemToList(sale);
        Transaction[] list = history.getItems();
        doThrow(new IOException()).when(mockAccountFileDAO).getTransactionFromPurchaseHistory("testUsername", saleID);
        // Invoke
        ResponseEntity<Transaction> response = accountController.getTransactionFromPurchaseHistory("testUsername", sale.getId());
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUsedCoupon() throws IOException { 
        // Setup
        Coupon[] list = new Coupon[1];
        Coupon coupon = new Coupon("test", 0.5);
        list[0] = coupon;
        when(mockAccountFileDAO.getUsedCouponsList("testUsername")).thenReturn(list);
        // Invoke
        ResponseEntity<Coupon[]> response = accountController.getUsedCouponsList("testUsername");
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUsedCouponError() throws IOException {
        // test that thrown IOException returns INTERNAL_SERVER_ERROR
        // Setup
        Coupon[] list = new Coupon[1];
        Coupon coupon = new Coupon("test", 0.5);
        list[0] = coupon;
        doThrow(new IOException()).when(mockAccountFileDAO).getUsedCouponsList("testUsername");
        // Invoke
        ResponseEntity<Coupon[]> response = accountController.getUsedCouponsList("testUsername");
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testApplyCoupon() throws IOException { 
        // Setup
        Coupon[] list = new Coupon[1];
        Coupon coupon = new Coupon("test", 0.5);
        list[0] = coupon;
        when(mockAccountFileDAO.applyCoupon("testUsername", coupon)).thenReturn(10.0);
        // Invoke
        ResponseEntity<Double> response = accountController.applyCouponToCart("testUsername", coupon);
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // @Test
    // public void testApplyCouponError() throws IOException {
    //     // test that thrown IOException returns INTERNAL_SERVER_ERROR
    //     // Setup
    //     Coupon[] list = new Coupon[1];
    //     Coupon coupon = new Coupon("test", 0.5);
    //     list[0] = coupon;
    //     doThrow(new IOException()).when(mockAccountFileDAO).applyCoupon("testCoupon", coupon);
    //     // Invoke
    //     ResponseEntity<Double> response = accountController.applyCouponToCart("testUsername", coupon);
    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    // }
}
