package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class WishlistTest 
{
    @Test
    public void testCtor(){
        // Setup
        Shoe[] actual = new Shoe[0];
        // Invoke
        Wishlist wishList = new Wishlist();

        // Analyze
        assertEquals(actual.length, wishList.getItems().length);
    }
    

    @Test
    public void TestAddToWishlist(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, wishList.getItems().length);
        assertEquals(shoe, wishList.getItem(shoe.getId()));
    }

    @Test
    public void TestWishlistLength(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(2, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);

        // Analyze
        int actual_length = 2;
        assertEquals(actual_length, wishList.getItems().length);
        System.out.println(Arrays.toString(wishList.getItems()));
    }

    @Test
    public void TestAddToWishlistBad(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(2, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe3 = new Shoe(3, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);
        wishList.addItemToList(shoe3);

        // Analyze
        int actual_length = 1;
        assertNotEquals(actual_length, wishList.getItems().length);
    }

    @Test
    public void TestGetShoeDoesntExist(){
        // Setup
        Wishlist wishList = new Wishlist();
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        int bad_id = 2;

        // Invoke
        wishList.addItemToList(shoe);

        // Analyze
        assertNull(wishList.getItem(bad_id));
    }

    @Test
    public void TestRemoveFromWishlist(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        // Invoke
        wishList.addItemToList(shoe);
        wishList.removeItemFromList(shoe.getId());
        
        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, wishList.getItems().length);
    }


    @Test
    public void TestRemoveFromWishlistBadID(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();
        int bad_id = 0;

        // Invoke
        wishList.addItemToList(shoe);
        wishList.removeItemFromList(bad_id);
        
        // Analyze
        int actual_length = 0;
        assertNotEquals(actual_length, wishList.getItems().length);
    }

    @Test
    public void TestUpdateInWishlist(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        Shoe updatedShoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Red", "Flat tops", 6.5, 120, 10);

        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);
        wishList.updateItemInList(updatedShoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, wishList.getItems().length);
        assertEquals(shoe, wishList.getItem(shoe.getId()));
    }

    @Test
    public void TestUpdateInWishlistBad(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();

        Shoe updatedShoe = new Shoe(2, "Converse", "Chuck Taylor", 1, "Red", "Flat tops", 6.5, 120, 10);

        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);
        Shoe fromUpdate = wishList.updateItemInList(updatedShoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, wishList.getItems().length);
        assertNull(fromUpdate);
    }

    @Test
    public void TestRemoveAllWishlistItem(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Wishlist wishList = new Wishlist();


        // Invoke
        wishList.addItemToList(shoe);
        wishList.addItemToList(shoe2);
        
        wishList.removeAllFromList();

        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, wishList.getItems().length);
    }


}
