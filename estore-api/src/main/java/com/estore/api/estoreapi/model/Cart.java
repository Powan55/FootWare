package com.estore.api.estoreapi.model;

import java.util.TreeMap;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mathew Owusu Jr, Matthew Zhang
 */
public class Cart implements ListInterface<Shoe> {
    private static final Logger LOG = Logger.getLogger(Wishlist.class.getName());
    @JsonProperty("items")
    private ArrayList<Shoe> items;

    /**
     * 
     */
    public Cart() {
        this.items = new ArrayList<>();
    }

    @Override
    public Shoe getItem(int id) {
        for (Shoe shoe : items) {
            if (shoe.getId() == id) {
                return shoe;
            }
        }
        return null;
    }

    @Override
    public Shoe[] getItems() {
        Shoe[] shoeArray = new Shoe[items.size()];
        items.toArray(shoeArray);
        return shoeArray;
    }

    @Override
    public Shoe addItemToList(Shoe shoe) {
        if (items.contains(shoe))
            return null;
        items.add(shoe);
        return shoe;
    }

    @Override
    public void removeItemFromList(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                break;
            }
        }
    }

    @Override
    public void removeAllFromList() {
        items.clear();
    }

    @Override
    public Shoe updateItemInList(Shoe shoe) {
        for (Shoe currentShoe : items) {
            if (currentShoe.equals(shoe)) {
                currentShoe.updateColor(shoe.getColor());
                currentShoe.updateSize(shoe.getSize());
                currentShoe.updatePrice(shoe.getPrice());
                currentShoe.updateQuantity(shoe.getQuantity());
                return currentShoe;
            }
        }
        return null;
    }

    public double applyCouponToCart(Coupon coupon) {
        double totalPrice = 0;
        for (Shoe shoe : items) {
            totalPrice += shoe.getPrice();
        }

        double moneyReduced = totalPrice * coupon.getDiscount();
        //double shoePriceReduceAmount = moneyReduced / items.size();

        for (Shoe shoe : items) {
            shoe.updatePrice(shoe.getPrice() - (shoe.getPrice() * coupon.getDiscount()));
        }

        return moneyReduced;
    }
}