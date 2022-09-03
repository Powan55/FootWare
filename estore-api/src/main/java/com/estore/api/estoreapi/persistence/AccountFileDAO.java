package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.conditional.ElseAction;

import com.estore.api.estoreapi.model.*;

/**
 * Implements the functionality for JSON file-based peristance for Account
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty, Matthew Zhang, Mathew Owusu Jr
 */
@Component
public class AccountFileDAO implements AccountDAO {
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    Map<String, Account> inventory; // Provides a local cache of the hero objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Hero
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new hero
    private String filename; // Filename to read from and write to

    /**
     * 
     * @param filename
     * @param objectMapper
     * @throws IOException
     */
    public AccountFileDAO(@Value("${account.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        inventory = new TreeMap<>();
        load(); // load the accounts from the file
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray(null);

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), accountArray);
        return true;
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean load() throws IOException {
        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);

        // Add each hero to the tree map
        for (Account account : accountArray) {
            inventory.put(account.getUserName(), account);
        }
        return true;
    }

    /**
     * @param containsText finds the accounts associated with the given name
     */
    @Override
    public Account[] findAccountsByUsername(String containsText) throws IOException {
        synchronized (inventory) {
            return getAccountArray(containsText);
        }
    }

    /**
     * 
     * @param username finds account with associated username
     * @return account with associated username
     * 
     *         searches if account username exists and returns it, otherwise return
     *         null
     */
    @Override
    public Account getAccount(String username) throws IOException {
        synchronized (inventory) {
            if (inventory.containsKey(username))
                return inventory.get(username);
            else
                return null;
        }
    }

    /**
     * returns list of all accounts
     */
    @Override
    public Account[] getAccounts() throws IOException {
        synchronized (inventory) {
            return getAccountArray(null);
        }
    }

    /**
     * 
     * @param containsText string to find matching accounts with
     * @return list of accounts with matching text
     */
    private Account[] getAccountArray(String containsText) {
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : inventory.values()) {
            if (containsText == null || account.getUserName().contains(containsText)) {
                accountArrayList.add(account);
            }
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * creates an account and returns it
     */
    @Override
    public Account createAccount(Account account) throws IOException {
        synchronized (inventory) {
            Account newAccount;
            newAccount = new Account(account.getUserName(), account.getDisplayName(),
                    account.getIsAdmin(), new Cart(), new Wishlist(), new PurchaseHistory(),
                    new ArrayList<Coupon>());
            for (Account existing : inventory.values()) {
                if (account.equals(existing)) {
                    return null;
                }
            }

            inventory.put(newAccount.getUserName(), newAccount);
            save();
            return newAccount;
        }
    }

    /**
     * deletes account if it exists within inventory
     */
    @Override
    public Boolean deleteAccount(String username) throws IOException {
        synchronized (inventory) {
            if (inventory.containsKey(username)) {
                inventory.remove(username);
                save();
                return true;
            }
            save();
            return false;
        }
    }

    /**
     * updates an account data if the passed username exists within inventory
     */
    @Override
    public Account updateAccount(String username, Account account) throws IOException {
        synchronized (inventory) {

            if (inventory.containsKey(username)) {
                Account existing = inventory.get(username);
                inventory.remove(username);

                existing.updateUserName(account.getUserName());
                existing.updateDisplayName(account.getDisplayName());

                inventory.put(existing.getUserName(), existing);
                save();
                return existing;
            }
            return null;
        }

    }

    /**
     * 
     */
    @Override
    public Shoe[] getCart(String username) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            Cart cart = account.getCart();
            return cart.getItems();
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Shoe getShoeFromCart(String username, int id) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            return account.getCart().getItem(id);
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Shoe addToCart(String username, Shoe shoe) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            Shoe addedShoe = account.getCart().addItemToList(shoe);
            save();
            return addedShoe;
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Boolean removeFromCart(String username, int id) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getCart().removeItemFromList(id);
            save();
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeAllFromCart(String username) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getCart().removeAllFromList();
            save();
            return true;
        }
        return false;
    }

    /**
     * 
     */
    @Override
    public Shoe updateShoeInCart(String username, Shoe shoe) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getCart().updateItemInList(shoe);
            save();
            return shoe;
        }
        return null;
    }

    /**
    * 
    */
    @Override
    public Shoe[] getWishlist(String username) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            Wishlist wishlist = account.getWishlist();
            return wishlist.getItems();
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Shoe getShoeFromWishlist(String username, int id) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            return account.getWishlist().getItem(id);
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Shoe addToWishlist(String username, Shoe shoe) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            Shoe addedShoe = account.getWishlist().addItemToList(shoe);
            save();
            return addedShoe;
        }
        return null;
    }

    /**
     * 
     */
    @Override
    public Boolean removeFromWishlist(String username, int id) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getWishlist().removeItemFromList(id);
            save();
            return true;
        }
        return false;
    }

    /**
     * 
     */
    @Override
    public Shoe updateShoeInWishlist(String username, Shoe shoe) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getWishlist().updateItemInList(shoe);
            save();
            return shoe;
        }
        return null;
    }

    @Override
    public Transaction addToPurchaseHistory(String username, Transaction transaction) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            account.getPurchaseHistory().addItemToList(transaction);
            for (Account otherAccount : Account.getAccounts()) {
                if (otherAccount.getIsAdmin()) {
                    otherAccount.getPurchaseHistory().addItemToList(transaction);
                }
            }
            save();
            return transaction;
        }
        return null;
    }

    @Override
    public Transaction getTransactionFromPurchaseHistory(String username, int id) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            return account.getPurchaseHistory().getItem(id);
        }
        return null;
    }
    
    @Override
    public double applyCoupon(String username, Coupon coupon) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            double Coupon = account.applyCoupon(coupon);
            save();
            return Coupon;
        }
        return 0;
    }

    @Override
    public Coupon[] getUsedCouponsList(String username) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            Coupon[] usedCoupons = account.getUsedCoupons();
            return usedCoupons;
        }
        return null;
    }

    @Override
    public Transaction[] getPurchaseHistory(String username) throws IOException {
        Account account = this.getAccount(username);
        if (account != null) {
            return account.getPurchaseHistory().getItems();
        }
        return null;
    }
}
