package com.estore.api.estoreapi.persistence;


import java.io.IOException;

/**
 * Defines the interface for Shoe object persistence
 * 
 * @author Matthew Zhang
 */
public interface ListDAO<Item> {
    

    /**
     * Adds a given shoe in the cart.
     * @param shoe Shoe to be added to the cart
     * @return
     */
    Item addItemToCart(Item item) throws IOException;

    /**
     * Remove's a given shoe from the carts
     * @param shoe Shoe to be removed from the cart
     * @return
     */
    Boolean deleteItemFromCart(int id) throws IOException;

    /**
     * Returns the number of given shoe in the cart
     * @param shoe shoe in the cart
     * @return number of shoe in the cart
     */
    int showItemQuantityInCart(Item item) throws IOException;

    /**
     * 
     * @param id finds shoe with associated id
     * @return shoe with associated id
     * @throws IOException
     */
    Item getItem(int id) throws IOException;

    /**
     * Search shoes from the cart inventory from given string
     * @param containsText text to besearch
     * @return Shoes that contain's given text 
     * @throws IOException
     */
    Item[] searchItem(String containsText) throws IOException;

    /**
     * List all shoe from the cart
     * @return list of shoes in the cart
     */
    Item[] showAllItemsFromCart() throws IOException;

}
