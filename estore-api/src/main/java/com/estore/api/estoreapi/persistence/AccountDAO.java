package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.*;

/**
 * Defines the interface for Account object persistence related to login
 * 
 * @author Matthew Zhang, Mathew Owusu Jr
 */
public interface AccountDAO {

    /**
     * 
     * @param containsText finds account with matching username or display name
     * @return
     * @throws IOException
     */
    Account[] findAccountsByUsername(String containsText) throws IOException;

    /**
     * 
     * @return list of all accounts
     * @throws IOException
     */
    Account[] getAccounts() throws IOException;

    /**
     * 
     * @param account
     * @return the created account
     * @throws IOException
     */
    Account createAccount(Account account) throws IOException;

    /**
     * 
     * @param name finds account with associated username
     * @return account with associated username
     * @throws IOException
     */
    Account getAccount(String username) throws IOException;

    /**
     * 
     * @return the updated account
     * @throws IOException
     */
    Account updateAccount(String username, Account account) throws IOException;

    /**
     * 
     * @param username
     * @return whether account was successfully deleted or not
     * @throws IOException
     */
    Boolean deleteAccount(String username) throws IOException;
    
    /**
     * 
     * @param username
     * @return
     * @throws IOException
     */
    Shoe[] getCart(String username) throws IOException;

    /**
     * 
     * @param username
     * @param id
     * @return
     * @throws IOException
     */
    Shoe getShoeFromCart(String username, int id) throws IOException;

    /**
     * 
     * @param username
     * @param shoe
     * @return
     * @throws IOException
     */
    Shoe addToCart(String username, Shoe shoe) throws IOException;

    /**
     * 
     * @param username
     * @param id
     * @throws IOException
     */
    Boolean removeFromCart(String username, int id) throws IOException;

    Boolean removeAllFromCart(String username) throws IOException;

    Shoe updateShoeInCart(String username, Shoe shoe) throws IOException;

        /**
     * 
     * @param username
     * @return
     * @throws IOException
     */
    Shoe[] getWishlist(String username) throws IOException;

    /**
     * 
     * @param username
     * @param id
     * @return
     * @throws IOException
     */
    Shoe getShoeFromWishlist(String username, int id) throws IOException;

    /**
     * 
     * @param username
     * @param shoe
     * @return
     * @throws IOException
     */
    Shoe addToWishlist(String username, Shoe shoe) throws IOException;

    /**
     * 
     * @param username
     * @param id
     * @throws IOException
     */
    Boolean removeFromWishlist(String username, int id) throws IOException;

    Shoe updateShoeInWishlist(String username, Shoe shoe) throws IOException;


    Transaction addToPurchaseHistory(String username, Transaction transaction) throws IOException;

    Transaction getTransactionFromPurchaseHistory(String username, int id) throws IOException;

    Transaction[] getPurchaseHistory(String username) throws IOException;

    public double applyCoupon(String username, Coupon coupon) throws IOException;
    

    public Coupon[] getUsedCouponsList(String username) throws IOException;
}
