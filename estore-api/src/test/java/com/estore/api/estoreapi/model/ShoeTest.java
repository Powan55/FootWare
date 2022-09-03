package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ShoeTest {
    private Shoe shoe = new Shoe(1, "Converse", "Chuck Taylor", 1, "Tan", "Flat tops", 9.5, 120, 10);

    @Test
    public void testCtor(){
        // Setup
        int expected_id = 1;
        String expected_brand = "Converse";
        String expected_name = "Chuck Taylor";
        int expected_model = 1;
        String expected_color = "Tan";
        String expected_shoeType = "Flat tops";
        double expected_size = 9.5;
        double expected_price = 120;
        int expected_quantity = 10;

        // Invoke
        Shoe testShoe = new Shoe(expected_id, expected_brand, expected_name,
        expected_model, expected_color, expected_shoeType, expected_size, expected_price,
        expected_quantity);

        // Analyze
        assertEquals(expected_id, testShoe.getId());
        assertEquals(expected_brand, testShoe.getBrand());
        assertEquals(expected_name, testShoe.getName());
        assertEquals(expected_model, testShoe.getModel());
        assertEquals(expected_color, testShoe.getColor());
        assertEquals(expected_shoeType, testShoe.getShoeType());
        assertEquals(expected_size, testShoe.getSize());
        assertEquals(expected_price, testShoe.getPrice());
        assertEquals(expected_quantity, testShoe.getQuantity());
    }
    
    @Test
    public void testName(){
        // Setup
        String expected_name = "Platformers";

        // Invoke
        shoe.updateName(expected_name);

        // Analyze
        assertEquals(expected_name, shoe.getName());
    }

    @Test
    public void testModel(){
        // Setup
        int expected_model = 350;

        // Invoke
        shoe.updateModel(expected_model);

        // Analyze
        assertEquals(expected_model, shoe.getModel());
    }

    @Test
    public void testColor(){
        // Setup
        String expected_color = "Red";

        // Invoke
        shoe.updateColor(expected_color);

        // Analyze
        assertEquals(expected_color, shoe.getColor());
    }

    @Test
    public void testPrice(){
        // Setup
        double expected_price = 400.99;

        // Invoke
        shoe.updatePrice(expected_price);

        // Analyze
        assertEquals(expected_price, shoe.getPrice());
    }

    @Test
    public void testSize(){
        // Setup
        double expected_size = 10;

        // Invoke
        shoe.updateSize(expected_size);

        // Analyze
        assertEquals(expected_size, shoe.getSize());
    }

    @Test
    public void testReplenishStock(){
        // Setup
        int expected_quantity = 15;
        int increase_amount = 5;

        // Invoke
        shoe.replenishStock(increase_amount);

        // Analyze
        assertEquals(expected_quantity, shoe.quantity);
    }

    @Test
    public void testRemoveFromStock(){
        // Setup
        int expected_quantity = 5;
        int decrease_amount = 5;

        // Invoke

        shoe.removeFromStock(decrease_amount);

        // Analyze
        assertEquals(expected_quantity, shoe.quantity);
    }

    @Test
    public void testEquals(){
        // Setup
        int id = 1;
        String brand = "Converse";
        String name = "Chuck Taylor";
        int model = 1;
        String color = "Tan";
        String shoeType = "Flat tops";
        double size = 9.5;
        double price = 120;
        int quantity = 10;

        // Invoke
        // Original
        Shoe testShoeA = new Shoe(id, brand, name, model, color, shoeType, size ,price,
        quantity);

        // Completely the same
        Shoe testShoeSame = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);
        
        // Different Brand
        brand = "Jordan";
        Shoe testShoeBrand = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);

        // Different name
        brand = testShoeA.getBrand();
        name = "Retro";
        Shoe testShoeName = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);

        // Different model
        name = testShoeA.getName();
        model = 4;
        Shoe testShoeModel = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);

        // Different color
        model = testShoeA.getModel();
        color = "Zebra";
        Shoe testShoeColor = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);

        // Completely Different
        id = 2;
        brand = "Yeezy";
        name = "Boost";
        model = 350;
        color = "Zebra";
        shoeType = "Athletic";
        price = 250;
        quantity = 10;

        Shoe testShoeDifferent = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);

        // Not Even a shoe
        Object notShoe = new Object();

        // Analyze
        assertEquals(testShoeA, testShoeSame);
        assertEquals(testShoeA, testShoeBrand);
        assertEquals(testShoeA, testShoeName);
        assertEquals(testShoeA, testShoeModel);
        assertEquals(testShoeA, testShoeColor);
        assertNotEquals(testShoeA, testShoeDifferent);
        assertNotEquals(testShoeA, notShoe);
    }

    @Test
    public void testToString(){
        // Setup
        int id = 1;
        String brand = "Converse";
        String name = "Chuck Taylor";
        int model = 1;
        String color = "Tan";
        String shoeType = "Flat tops";
        double size = 9.5;
        double price = 120;
        int quantity = 10;

        Shoe testShoe = new Shoe(id, brand, name, model, color, shoeType, size, price,
        quantity);
        // Invoke
        String expected_toString = "Shoe [productId=1, brand=Converse, name=Chuck Taylor, model=1, shoeType=Flat tops, size=9.5, price=120.00, quantity=10]";

        // Analyze
        assertEquals(expected_toString, testShoe.toString());
    }
}