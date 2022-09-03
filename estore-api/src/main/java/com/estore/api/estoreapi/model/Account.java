package com.estore.api.estoreapi.model;

import java.util.*;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Account entity
 * 
 * @author Matthew Zhang, Mathew Owusu Jr
 */
public class Account implements Comparable<Account> {
    private static final Logger LOG = Logger.getLogger(Account.class.getName());
    static final String STRING_FORMAT = "Account [userName=%s, displayName=%s isAdmin=%b]";
    public static ArrayList<Account> accounts = new ArrayList<>();

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("isAdmin")
    private boolean isAdmin = false;
    @JsonProperty("cart")
    private Cart cart = new Cart();
    @JsonProperty("wishlist")
    private Wishlist wishlist = new Wishlist();
    @JsonProperty("purchaseHistory")
    private PurchaseHistory purchaseHistory = new PurchaseHistory();
    @JsonProperty("usedCoupons")
    private ArrayList<Coupon> usedCoupons = new ArrayList<Coupon>();

    /**
     * Creates an Account
     * 
     * @param name the name of the account
     */
    public Account(@JsonProperty("userName") String userName,
            @JsonProperty("displayName") String displayName,
            @JsonProperty("isAdmin") boolean isAdmin,
            @JsonProperty("cart") Cart cart,
            @JsonProperty("wishlist") Wishlist wishlist,
            @JsonProperty("purchaseHistory") PurchaseHistory purchaseHistory,
            @JsonProperty("usedCoupons") ArrayList<Coupon> usedCoupons) {
        this.userName = userName;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
        this.cart = cart;
        this.wishlist = wishlist;
        this.purchaseHistory = purchaseHistory;
        this.usedCoupons = usedCoupons;
        accounts.add(this);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * 
     * @return whether account is admin or not
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 
     * @return the account's userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @return the account's displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @return the account's associated cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * 
     * @return the account's associated wishlist
     */
    public Wishlist getWishlist() {
        return wishlist;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * Changes the account's username
     * 
     * @param name the new name
     */
    public void updateUserName(String newName) {
        this.userName = newName;
    }

    /**
     * Changes the account's display name
     * 
     * @param name the new name
     */
    public void updateDisplayName(String newName) {
        this.displayName = newName;
    }

    public double applyCoupon(Coupon coupon) {
        usedCoupons.add(coupon);
        return cart.applyCouponToCart(coupon);
    }

    public Coupon[] getUsedCoupons() {
        Coupon[] couponArray = new Coupon[usedCoupons.size()];
        usedCoupons.toArray(couponArray);
        return couponArray;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Account) {
            Account otherAccount = (Account) other;
            return this.userName.equals(otherAccount.userName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.userName.hashCode();
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName, displayName, isAdmin);
    }

    @Override
    public int compareTo(Account other) {
        return this.userName.compareTo(other.userName);
    }
}
