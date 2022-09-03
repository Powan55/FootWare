package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.persistence.AccountFileDAO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;
import java.io.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;


/**
 * @author Mathew Owusu Jr, Laxmi Poudel, Matthew Zhang
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    // CuT
    private AccountFileDAO accountFileDAO;

    // Mock object
    private ObjectMapper mockObjectMapper;
    private Cart mockCart;
    private Wishlist mockWishlist;
    private PurchaseHistory mockPurchaseHistory;
    private ArrayList<Coupon> mockCoupons;
    // Test objects
    private Account[] mockAccountList;

    @BeforeEach
    public void setup() throws StreamReadException, DatabindException, IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        mockAccountList = new Account[3];
        mockCart = new Cart();
        mockWishlist = new Wishlist();
        mockPurchaseHistory = new PurchaseHistory();
        mockCoupons = new ArrayList<>();


        mockAccountList[0] = new Account("username", "displayname", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);
        mockAccountList[1] = new Account("Admin", "Admin", true, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);
        mockAccountList[2] = new Account("username1", "displayname1", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);
        
        Arrays.sort(mockAccountList);
        when(mockObjectMapper.readValue(new File("file.txt"), Account[].class)).thenReturn(mockAccountList);
        accountFileDAO = new AccountFileDAO("file.txt", mockObjectMapper);
    }

    @Test
    public void testFindAccountsByUsername() throws IOException {

        // fetches all accounts that match the string "username" and check if got the
        // right accounts
        Account[] accounts = accountFileDAO.findAccountsByUsername("username");
        assertEquals(mockAccountList[1], accounts[0]);
        assertEquals(mockAccountList[2], accounts[1]);

        // fetches all accounts and check if it equals the mock list
        Account[] accounts2 = accountFileDAO.findAccountsByUsername(null);
        Arrays.sort(accounts2);
        assertArrayEquals(mockAccountList, accounts2);

    }

    @Test
    public void testGetAccount() throws IOException {
        // check that account exist
        Account account = accountFileDAO.getAccount("username");
        assertEquals(mockAccountList[1], account);

        // check that account doesn't exist
        Account nullAccount = accountFileDAO.getAccount("random");
        assertNull(nullAccount);
    }

    @Test
    public void testGetAccounts() throws IOException {
        // check that returned list is equal to mocked list
        Account[] accounts = accountFileDAO.getAccounts();
        assertArrayEquals(mockAccountList, accounts);
    }

    @Test
    public void testCreateAccount() throws IOException {
        // create new account and check that its in inventory
        Account newAccount = new Account("new", "new", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons);
        accountFileDAO.createAccount(newAccount);
        assertEquals(newAccount, accountFileDAO.getAccount("new"));

        // check that creating an account that already exists is null
        Account account = accountFileDAO.createAccount(mockAccountList[0]);
        assertNull(account);
    }

    @Test
    public void testUpdateAccount() throws IOException {
        // updates a existing account and check that old account is null and new exists
        accountFileDAO.updateAccount("username", new Account("newuser", "newuser", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons));
        assertNull(accountFileDAO.getAccount("username"));
        assertNotNull(accountFileDAO.getAccount("newuser"));

        // check that updating a nonexistent account returns null
        assertNull(accountFileDAO.updateAccount("none", new Account("test", "test", false, mockCart, mockWishlist, mockPurchaseHistory, mockCoupons)));
    }

    @Test
    public void testDeleteAccount() throws IOException {
        // test that existing account was deleted successfully
        boolean passed = accountFileDAO.deleteAccount("username");
        assertTrue(passed);

        // test a nonexistent account returned false
        boolean failed = accountFileDAO.deleteAccount("none");
        assertFalse(failed);
    }

    @Test
    public void testGetCart() throws IOException{
        Shoe[] cart = accountFileDAO.getCart("username");
        Shoe[] actual = new Shoe[0];
        assertEquals(actual.length, cart.length);

        // test nonexistent account
        Shoe[] cart2 = accountFileDAO.getCart("username9");
        assertNull(cart2);
    }

    @Test
    public void testGetFromCart() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToCart("username", shoe);
        assertEquals(shoe, accountFileDAO.getShoeFromCart("username", shoe.getId()));

        // test nonexistent account
        Shoe addedShoe2 = accountFileDAO.getShoeFromCart("username9", shoe.getId());
        assertNull(addedShoe2);
    }

    @Test
    public void testAddToCart() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        Shoe addedShoe = accountFileDAO.addToCart("username", shoe);
        assertEquals(shoe, addedShoe);

        // test nonexistent account
        Shoe addedShoe2 = accountFileDAO.addToCart("username9", shoe);
        assertNull(addedShoe2);
    }

    @Test
    public void testUpdateCart() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToCart("username", shoe);
        Shoe updateShoe = new Shoe(5, "Nike", "Air", 500, "Zebra", "ATHLETIC", 9.5, 50.0, 5);
        assertEquals(shoe, accountFileDAO.updateShoeInCart("username", updateShoe));

        // test nonexistent account
        Shoe failed = accountFileDAO.updateShoeInCart("username9", shoe);
        assertNull(failed);
    }

    @Test
    public void testRemoveFromCart() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToCart("username", shoe);
        boolean actual = accountFileDAO.removeFromCart("username", shoe.getId());
        assertEquals(true, actual);

        // test nonexistent account
        boolean failed = accountFileDAO.removeFromCart("username9", shoe.getId());
        assertEquals(false, failed);
    }

    @Test
    public void testGetFromWishlist() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToWishlist("username", shoe);
        assertEquals(shoe, accountFileDAO.getShoeFromWishlist("username", shoe.getId()));

        // test nonexistent account
        Shoe addedShoe2 = accountFileDAO.getShoeFromWishlist("username9", shoe.getId());
        assertNull(addedShoe2);
    }

    @Test
    public void testAddToWishlist() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        Shoe addedShoe = accountFileDAO.addToWishlist("username", shoe);
        assertEquals(shoe, addedShoe);

        // test nonexistent account
        Shoe addedShoe2 = accountFileDAO.addToWishlist("username9", shoe);
        assertNull(addedShoe2);
    }

    @Test
    public void testRemoveFromWishlist() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToWishlist("username", shoe);
        boolean actual = accountFileDAO.removeFromWishlist("username", shoe.getId());
        assertEquals(true, actual);

        // test nonexistent account
        boolean failed = accountFileDAO.removeFromWishlist("username9", shoe.getId());
        assertEquals(false, failed);
    }

    @Test
    public void testGetWishlist() throws IOException{
        Shoe[] wishlist = accountFileDAO.getWishlist("username");
        Shoe[] actual = new Shoe[0];
        assertEquals(actual.length, wishlist.length);

        // test nonexistent account
        Shoe[] wishlist2 = accountFileDAO.getWishlist("username9");
        assertNull(wishlist2);
    }

    @Test
    public void testUpdateWishlist() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToWishlist("username", shoe);
        Shoe updateShoe = new Shoe(5, "Nike", "Air", 500, "Zebra", "ATHLETIC", 9.5, 50.0, 5);
        assertEquals(shoe, accountFileDAO.updateShoeInWishlist("username", updateShoe));

        // test nonexistent account
        Shoe failed = accountFileDAO.updateShoeInWishlist("username9", shoe);
        assertNull(failed);
    }

    @Test
    public void testRemoveAllFromCart() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToCart("username", shoe);
        boolean actual = accountFileDAO.removeAllFromCart("username");
        assertEquals(true, actual);

        // test nonexistent account
        boolean failed = accountFileDAO.removeAllFromCart("username9");
        assertEquals(false, failed);
    }

    @Test
    public void testGetShoeFromWishlist() throws IOException{
        Shoe shoe = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        accountFileDAO.addToWishlist("username", shoe);
        Shoe wishlist = accountFileDAO.getShoeFromWishlist("username", 5);
        
        assertEquals(shoe, wishlist);
    }

    @Test
    public void testAddTransaction() throws IOException{
        Shoe shoe[] = new Shoe[2];
        shoe[0] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        shoe[1] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);

        Transaction transaction = new Transaction(100, shoe);
        accountFileDAO.addToPurchaseHistory("username", transaction);

        Transaction transactionList[] = accountFileDAO.getPurchaseHistory("username");

        assertEquals(null,  accountFileDAO.addToPurchaseHistory("", transaction));
        assertEquals(transaction, transactionList[0]);
    }

    @Test
    public void testGetPurchaseHistory() throws IOException{
        Shoe shoe[] = new Shoe[2];
        shoe[0] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        shoe[1] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);

        Transaction transaction = new Transaction(100, shoe);
        accountFileDAO.addToPurchaseHistory("username", transaction);
        Transaction transactionList[] = accountFileDAO.getPurchaseHistory("username");

        
        assertEquals(transaction, transactionList[0]);
        assertEquals(null, accountFileDAO.getPurchaseHistory(""));
    }
    

    @Test
    public void testGetTransactionFromPurchaseHistory() throws IOException{
        Shoe shoe[] = new Shoe[2];
        shoe[0] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        shoe[1] = new Shoe(5, "Nike", "Air", 500, "White", "ATHLETIC", 9, 50.0, 5);
        

        Transaction transaction = new Transaction(100, shoe);
        accountFileDAO.addToPurchaseHistory("username", transaction);
        Transaction actual = accountFileDAO.getTransactionFromPurchaseHistory("username", transaction.getId().hashCode());
        
        assertEquals(transaction, actual);
        assertEquals(null, accountFileDAO.getTransactionFromPurchaseHistory("", transaction.getId().hashCode()));
    }

    @Test
    public void testApplyCoupon() throws IOException{
        Coupon coupon = new Coupon("code", 15.0);
        accountFileDAO.applyCoupon("username", coupon);
        accountFileDAO.applyCoupon("", coupon);
        
        Coupon listCoupon[] = accountFileDAO.getUsedCouponsList("username");

        assertEquals(coupon, listCoupon[0]);
        assertEquals(null, accountFileDAO.getUsedCouponsList(""));
    }

}
