package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.AccountDAO;

import com.estore.api.estoreapi.model.*;

/**
 * Handles the REST API requests for the Shoe resource
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty, Matthew Zhang, Mathew Owusu Jr
 */

@RestController
@RequestMapping("accounts")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * 
     * @param username used to find the associated account if it exists
     * @return the account with the associated username
     */

    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccount(@PathVariable String username) {
        LOG.info("GET /account/" + username);
        try {
            Account account = accountDAO.getAccount(username);
            if (account != null)
                return new ResponseEntity<Account>(account, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @return a list of all existing accounts
     */
    @GetMapping("")
    public ResponseEntity<Account[]> getAccounts() {
        LOG.info("GET /accounts");

        try {
            Account[] accountList = accountDAO.getAccounts();
            return new ResponseEntity<>(accountList, HttpStatus.OK);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param account data used to create account
     * @return the newly created account
     */
    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOG.info("POST /account " + account);

        try {
            Account newAccount = accountDAO.createAccount(account);
            if (newAccount != null) {
                return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param username username of associated account
     * @return status to indicate if operation was successful
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Account> deleteAccount(@PathVariable String username) {
        LOG.info("DELETE /account " + username);
        try {
            if (accountDAO.deleteAccount(username)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for searching for a account by username
     * 
     * @param username
     * @return list of accounts with matching username
     */
    @GetMapping("/")
    public ResponseEntity<Account[]> findAccountsByUsername(@RequestParam String username) {
        LOG.info("GET /account/?username=" + username);

        try {
            Account[] accounts = accountDAO.findAccountsByUsername(username);
            if (accounts != null)
                return new ResponseEntity<Account[]>(accounts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param account
     * @return account with new data
     */
    @PutMapping("/{username}")
    public ResponseEntity<Account> updateAccount(@PathVariable String username,
            @RequestBody Account account) {
        try {
            Account newAccount = accountDAO.updateAccount(username, account);
            if (newAccount != null) {
                return new ResponseEntity<Account>(newAccount, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 
     * @param username
     * @return
     */
    @GetMapping("/{username}/cart")
    public ResponseEntity<Shoe[]> getCart(@PathVariable String username){
        LOG.info("GET /" + username + "/cart");
        try {
            Shoe[] cart = accountDAO.getCart(username);
            return new ResponseEntity<Shoe[]>(cart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param username
     * @param id
     * @return
     */
    @GetMapping("/{username}/cart/{id}")
    public ResponseEntity<Shoe> getShoeFromCart(@PathVariable String username, @PathVariable int id){
        LOG.info("GET /" + username + "/cart/" + id);
        try {
            Shoe shoe = accountDAO.getShoeFromCart(username, id);
            if (shoe != null){
                return new ResponseEntity<Shoe>(shoe, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param username
     * @param shoe
     * @return
     */
    @PostMapping("/{username}/cart")
    public ResponseEntity<Shoe> addToCart(@PathVariable String username, @RequestBody Shoe shoe){
        LOG.info("POST /" + username + "/cart/" + shoe);
        try {
            Shoe newShoe = accountDAO.addToCart(username, shoe);
            if (newShoe != null){
                return new ResponseEntity<Shoe>(newShoe, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/cart/{id}")
    public ResponseEntity<Shoe> removeFromCart(@PathVariable String username, @PathVariable int id){
        LOG.info("DELETE /" + username + "/cart/" + id);
        try {
            if (accountDAO.removeFromCart(username, id)){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * 
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/cart/all")
    public ResponseEntity<Shoe[]> removeAllFromCart(@PathVariable String username){
        LOG.info("DELETE ALL /" + username + "/cart/");
        try {
            if (accountDAO.removeAllFromCart(username)){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param username
     * @param shoe
     * @return
     */
    @PutMapping("/{username}/cart")
    public ResponseEntity<Shoe> updateShoeInCart(@PathVariable String username, @RequestBody Shoe shoe){
        LOG.info("PUT /" + username + "/cart/" + shoe);
        try {
            Shoe newShoe = accountDAO.updateShoeInCart(username, shoe);
            if (newShoe != null){
                return new ResponseEntity<Shoe>(newShoe, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


     /**
     * 
     * @param username
     * @return
     */
    @GetMapping("/{username}/wishlist")
    public ResponseEntity<Shoe[]> getWishlist(@PathVariable String username){
        LOG.info("GET /" + username + "/cart");
        try {
            Shoe[] wishlist = accountDAO.getWishlist(username);
            return new ResponseEntity<Shoe[]>(wishlist, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param username
     * @param id
     * @return
     */
    @GetMapping("/{username}/wishlist/{id}")
    public ResponseEntity<Shoe> getShoeFromWishlist(@PathVariable String username, @PathVariable int id){
        LOG.info("GET /" + username + "/wishlist/" + id);
        try {
            Shoe shoe = accountDAO.getShoeFromWishlist(username, id);
            if (shoe != null){
                return new ResponseEntity<Shoe>(shoe, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param username
     * @param shoe
     * @return
     */
    @PostMapping("/{username}/wishlist")
    public ResponseEntity<Shoe> addToWishlist(@PathVariable String username, @RequestBody Shoe shoe){
        LOG.info("POST /" + username + "/wishlist/" + shoe);
        try {
            Shoe newShoe = accountDAO.addToWishlist(username, shoe);
            if (newShoe != null){
                return new ResponseEntity<Shoe>(newShoe, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/wishlist/{id}")
    public ResponseEntity<Shoe> removeFromWishlist(@PathVariable String username, @PathVariable int id){
        LOG.info("DELETE /" + username + "/cart/" + id);
        try {
            if (accountDAO.removeFromWishlist(username, id)){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 
     * @param username
     * @param transaction
     * @return
     */
    @PostMapping("/{username}/purchases")
    public ResponseEntity<Transaction> addToPurchaseHistory(@PathVariable String username, 
    @RequestBody Transaction transaction){
        LOG.info("POST /" + username + "/purchases/" + transaction);
        try {
            Transaction newTransaction = accountDAO.addToPurchaseHistory(username, transaction);
            if (newTransaction != null){
                return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
          } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        
    /**
     * 
     * @param username
     * @return
     */
    @GetMapping("/{username}/purchases")
    public ResponseEntity<Transaction[]> getPurchaseHistory(@PathVariable String username){
        LOG.info("GET /" + username + "/purchases");
        try {
            Transaction[] purchaseHistory = accountDAO.getPurchaseHistory(username);
            return new ResponseEntity<Transaction[]>(purchaseHistory, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
    /**
     * 
     * @param username
     * @param id
     * @return
     */
    @GetMapping("/{username}/purchases/{id}")
    public ResponseEntity<Transaction> getTransactionFromPurchaseHistory(@PathVariable String username, 
    @PathVariable String id){
        LOG.info("GET /" + username + "/purchases/" + id);
        try {
            Transaction transaction = accountDAO.getTransactionFromPurchaseHistory(username, id.hashCode());
            if (transaction != null){
                return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param username
     * @return
     */
    @GetMapping("/{username}/usedcoupons")
    public ResponseEntity<Coupon[]> getUsedCouponsList(@PathVariable String username){
        LOG.info("GET /" + username + "/usedcoupons");
        try {
            Coupon[] usedCoupons = accountDAO.getUsedCouponsList(username);
                return new ResponseEntity<Coupon[]>(usedCoupons, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param username
     * @param coupon
     * @return
     */
    @PutMapping("/{username}/cart/coupon")
    public ResponseEntity<Double> applyCouponToCart(@PathVariable String username, @RequestBody Coupon coupon){
        LOG.info("PUT /" + username + "/cart/" + coupon);
        try {
            double moneySaved = accountDAO.applyCoupon(username, coupon);
                return new ResponseEntity<Double>(moneySaved, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     

}
