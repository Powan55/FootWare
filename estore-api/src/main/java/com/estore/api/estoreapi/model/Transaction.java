package com.estore.api.estoreapi.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
    @JsonProperty("id") private String id;
    @JsonProperty("price") private double price; 
    @JsonProperty("shoes") private ArrayList<Shoe> shoes;

    public Transaction(@JsonProperty("price") double price, @JsonProperty("shoes") Shoe[] shoes){
        this.price = price;
        this.shoes = new ArrayList<>(Arrays.asList(shoes));
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }

    public Shoe[] getShoes() {
        return shoes.toArray(new Shoe[0]);
    }

    public Transaction updateTransaction(Transaction newTransaction) {
        this.price = newTransaction.price;
        this.shoes = newTransaction.shoes;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Transaction) {
            Transaction otherTransaction = (Transaction) other;
            return this.id.equals(otherTransaction.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.id + Arrays.toString(this.shoes.toArray(new Shoe[0])) + this.price;
    }

    @Override
    public int hashCode(){
        return this.id.hashCode();
    }

}
