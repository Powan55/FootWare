package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Shoe entity
 * 
 * @author Mathew Owusu Jr
 */
public class Shoe {
    private static final Logger LOG = Logger.getLogger(Shoe.class.getName());
    static final String STRING_FORMAT = "Shoe [productId=%d, brand=%s, name=%s, model=%d, shoeType=%s, size=%.1f, price=%.2f, quantity=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("model") private int model;
    @JsonProperty("brand") private String brand;
    @JsonProperty("name") private String name;
    @JsonProperty("color") private String color;
    @JsonProperty("shoeType") private String shoeType;
    @JsonProperty("price") private double price;
    @JsonProperty("size") protected double size;
    @JsonProperty("quantity") protected int quantity;

    /**
     * Creates a Shoe
     * @param id a number ID that identitfies a shoe
     * @param brand a brand name
     * @param name the name of the shoe
     * @param model its model number in its series
     * @param color its color
     * @param shoeType a string specifing its enum
     * @param size a shoes size
     * @param price its price
     * @param quantity how much quantity for each shoe size
     */
    public Shoe(@JsonProperty("id")  int id,
        @JsonProperty("brand")  String brand, 
        @JsonProperty("name") String name,
        @JsonProperty("model") int model,
        @JsonProperty("color") String color,
        @JsonProperty("shoeType") String shoeType,
        @JsonProperty("size") double size,
        @JsonProperty("price") double price,
        @JsonProperty("quantity") int quantity){
            this.id = id;
            this.brand = brand;
            this.name = name;
            this.model = model;
            this.color = color;
            this.shoeType = shoeType;
            this.size = size;
            this.price = price;
            this.quantity = quantity;
    }

    /**
     * 
     * @return the ID
     */
    public int getId(){
        return id;
    }

    /**
     * 
     * @return the brand name
     */
    public String getBrand(){
        return brand;
    }

    /**
     * 
     * @return the shoe's name
     */
    public String getName(){
        return name;
    }

    /**
     * 
     * @return the model number
     */
    public int getModel(){
        return model;
    }

    /**
     * 
     * @return the shoe's color
     */
    public String getColor(){
        return color;
    }

    /**
     * 
     * @return the shoe's type
     */
    public String getShoeType(){
        return shoeType;
    }


    public double getSize(){
        return size;
    }
    
    /**
     * 
     * @return the price of the shoe
     */
    public double getPrice(){
        return price;
    }

    /**
     * 
     * @return the initial quantity for each shoe size
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Changes the shoe's name
     * @param name the new name
     */
    public void updateName(String name){
        this.name = name;
    }

    /**
     * Changes the shoe's model number
     * @param model the new model number
     */
    public void updateModel(int model){
        this.model = model;
    }

    /**
     * Changes the shoe's color
     * @param color the new color
     */
    public void updateColor(String color){
        this.color = color;
    }

    /**
     * Changes the shoe's price
     * @param price the new price
     */
    public void updatePrice(double price){
        this.price = price;
    }

    public void updateQuantity(int amount) {
        this.quantity = amount;
    }


    /**
     * This function changes the shoe size
     * @param size The shoe size
     */
    public void updateSize(double size){
        this.size = size;
    }

    /**
     * Adds to the quantity of a specified size
     * @param amount how many shoes you want to add
     */
    public void replenishStock(int amount){
        this.quantity += amount;
    }

    /**
     * Removes a specified amount of shoes from a specified shoe size
     * @param amount the quantity being removed from stock
     */
    public void removeFromStock(int amount){
        this.quantity -= amount;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Shoe){
            Shoe otherShoe = (Shoe) other;
            return id == otherShoe.id;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, brand, name, model, shoeType, size, price, quantity);
    }
}
