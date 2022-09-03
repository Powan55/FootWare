package com.estore.api.estoreapi.model;

import java.util.TreeMap;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mathew Owusu Jr, Matthew Zhang
 */
public class Wishlist implements ListInterface<Shoe> {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    @JsonProperty("items")
    private ArrayList<Shoe> wishlist;

    /**
     * 
     */
    public Wishlist() {
        this.wishlist = new ArrayList<>();
    }

    @Override
    public Shoe getItem(int id) {
        for (Shoe shoe : wishlist) {
            if (shoe.getId() == id) {
                return shoe;
            }
        }
        return null;
    }

    @Override
    public Shoe[] getItems() {
        return wishlist.toArray(new Shoe[0]);
    }

    public Shoe addItemToList(Shoe shoe) {
        if (wishlist.contains(shoe))
            return null;
        wishlist.add(shoe);
        return shoe;
    }

    public void removeItemFromList(int id) {
        for (int i = 0; i < wishlist.size(); i++) {
            if (wishlist.get(i).getId() == id) {
                wishlist.remove(i);
                break;
            }
        }
    }

    public void removeAllFromList() {
        wishlist.clear();
    }

    public Shoe updateItemInList(Shoe shoe) {
        for (Shoe currentShoe : wishlist) {
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
}