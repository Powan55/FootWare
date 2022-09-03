package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CartTest {
    
    @Test
    public void testCtor(){
        // Setup
        Shoe[] actual = new Shoe[0];
        // Invoke
        Cart cart = new Cart();

        // Analyze
        assertEquals(actual.length, cart.getItems().length);
    }

    @Test
    public void TestAddToCart(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(2, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();

        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);

        // Analyze
        int actual_length = 2;
        assertEquals(actual_length, cart.getItems().length);
        assertEquals(shoe, cart.getItem(shoe.getId()));
    }

    @Test
    public void TestCartLength(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(2, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();

        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);

        // Analyze
        int actual_length = 2;
        assertEquals(actual_length, cart.getItems().length);
        System.out.println(Arrays.toString(cart.getItems()));
    }

    @Test
    public void TestAddToCartBad(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(2, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe3 = new Shoe(3, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();

        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);
        cart.addItemToList(shoe3);

        // Analyze
        int actual_length = 1;
        assertNotEquals(actual_length, cart.getItems().length);
        //assertEquals(shoe, cart.getItem(shoe.getId()));
    }

    @Test
    public void TestGetShoeDoesntExist(){
        // Setup
        Cart cart = new Cart();
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        int bad_id = 2;
        // Invoke
        cart.addItemToList(shoe);
        // Analyze
        assertNull(cart.getItem(bad_id));
    }

    @Test
    public void TestRemoveFromCart(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();
        cart.addItemToList(shoe);

        // Invoke
        cart.removeItemFromList(shoe.getId());
        
        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, cart.getItems().length);
    }

    @Test
    public void TestRemoveFromCartBadID(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();
        cart.addItemToList(shoe);
        int bad_id = 0;
        // Invoke
        cart.removeItemFromList(bad_id);
        // Analyze
        int actual_length = 0;
        assertNotEquals(actual_length, cart.getItems().length);
    }

    @Test
    public void TestUpdateInCart(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();

        Shoe updatedShoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Red", "Flat tops", 6.5, 120, 10);

        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);
        cart.updateItemInList(updatedShoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, cart.getItems().length);
        assertEquals(shoe, cart.getItem(shoe.getId()));
    }

    @Test
    public void TestUpdateInCartBad(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();

        Shoe updatedShoe = new Shoe(2, "Converse", "Chuck Taylor", 1, "Red", "Flat tops", 6.5, 120, 10);

        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);
        Shoe fromUpdate = cart.updateItemInList(updatedShoe);

        // Analyze
        int actual_length = 1;
        assertEquals(actual_length, cart.getItems().length);
        assertNull(fromUpdate);
    }

    @Test
    public void TestRemoveAllWishlistItem(){
        // Setup
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);
        Shoe shoe2 = new Shoe(1, "Nike", "Air", 3, "Tan", "Flat tops", 9.5, 120, 10);
        Cart cart = new Cart();


        // Invoke
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);
        
        cart.removeAllFromList();

        // Analyze
        int actual_length = 0;
        assertEquals(actual_length, cart.getItems().length);
    }

    @Test
    public void TestApplyToCart(){
        // Setup
        Cart cart = new Cart();
        Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 50, 10);
        Shoe shoe2 = new Shoe(2, "Yeezy", "Boost", 2, "Black", "Flat tops", 9.5, 50, 10);
        cart.addItemToList(shoe);
        cart.addItemToList(shoe2);
        Coupon deal = new Coupon("test", 0.5);

        double totalPrice = 0;
        for (Shoe shoes : cart.getItems()) {
            totalPrice += shoes.getPrice();
        }

        // Invoke
        double result = cart.applyCouponToCart(deal);

        // Analyze
        assertEquals(cart.getItems().length, 2);
        assertEquals(100.00, totalPrice);
        assertEquals(50.0, result);
        

    }
}
