package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.*;

/**
 * Implements the functionality for JSON file-based peristance for Shoe
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty, Mathew Owusu Jr, Matthew Zhang, Laxmi Poudel, Glenn Vodra
 * @param <E>
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer, Shoe> inventory; // Provides a local cache of the hero objects
                                  // so that we don't need to read from the file
                                  // each time
    private ObjectMapper objectMapper; // Provides conversion between Hero
                                       // objects and JSON text format written
                                       // to the file
    private String filename; // Filename to read from and write to

    /**
     * 
     * @param filename
     * @param objectMapper
     * @throws IOException
     */
    public InventoryFileDAO(@Value("${inventory.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        inventory = new TreeMap<>();
        load(); // load the shoes from the file
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean save() throws IOException {
        Shoe[] shoeArray = getShoeArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), shoeArray);
        return true;
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean load() throws IOException {

        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Shoe[] shoeArray = objectMapper.readValue(new File(filename), Shoe[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Shoe shoe : shoeArray) {
            inventory.put(shoe.getId(), shoe);
        }

        return true;
    }

    /**
     * Function that searches shoe fields 
     * @param containsText String from search
     */
    @Override
    public Shoe[] findShoes(String containsText) {
        synchronized(inventory) {
            ArrayList<Shoe> shoeArrayList = new ArrayList<>();
            for (Shoe shoe : inventory.values()) {
                if (containsText == null || 
                    shoe.getName().contains(containsText) ||
                    shoe.getBrand().contains(containsText) ||
                    shoe.getColor().contains(containsText) ||
                    shoe.getShoeType().contains(containsText) ||
                    containsText.contains(Integer.toString(shoe.getModel())) ||
                    containsText.contains(Double.toString(shoe.getPrice()))) {
                    shoeArrayList.add(shoe);
                }
            }
        Shoe[] shoeArray = new Shoe[shoeArrayList.size()];
        shoeArrayList.toArray(shoeArray);
        return shoeArray;
        }
    }

    /**
     * 
     * @param id finds shoe with associated id
     * @return shoe with associated id
     * 
     *         searches if shoe id is contained in inventory and returns it,
     *         otherwise return null
     */
    @Override
    public Shoe getShoe(int id) {
        synchronized (inventory) {
            if (inventory.containsKey(id))
                return inventory.get(id);
            else
                return null;
        }
    }

    /**
     * 
     */
    @Override
    public Shoe[] getShoes() {
        synchronized(inventory) {
            return getShoeArray();
        }
    }

    /**
     * 
     * @return
     */
    private Shoe[] getShoeArray(){
        ArrayList<Shoe> shoeArrayList = new ArrayList<>();

        for (Shoe shoe : inventory.values()) {
            shoeArrayList.add(shoe);
        }
        Shoe[] shoeArray = new Shoe[shoeArrayList.size()];
        shoeArrayList.toArray(shoeArray);
        return shoeArray;
    }

    /**
     * 
     */
    @Override
    public Shoe createShoe(Shoe shoe) throws IOException {
        synchronized (inventory) {
            if (inventory.containsKey(shoe.getId())) {
                return inventory.get(shoe.getId());
            }
            inventory.put(shoe.getId(), shoe);
            save();
            return shoe;
        }
    }

    @Override
    public Boolean deleteShoe(int id) throws IOException {
        synchronized (inventory) {
            if (inventory.containsKey(id)) {
                inventory.remove(id);
                save();
                return true;
            }
            save();
            return false;
        }
    }

    /**
     * 
     */
    @Override
    public Shoe updateShoe(Shoe shoe) throws IOException {
        synchronized (inventory) {

            if (inventory.containsKey(shoe.getId())) {
                inventory.put(shoe.getId(), shoe);
                for (Account account : Account.getAccounts()) {
                    account.getWishlist().updateItemInList(shoe);
                    account.getCart().updateItemInList(shoe);
                }
                return shoe;
            }

            return null;
        }

    }

    @Override
    public Shoe[] decrementShoes(Shoe[] shoes) throws IOException {
        synchronized (inventory) {

            for (Shoe shoe : shoes) {
                if (inventory.containsKey(shoe.getId())) {

                    shoe.updateQuantity(shoe.getQuantity() - 1);
                    if (shoe.getQuantity() > 0)
                        inventory.put(shoe.getId(), shoe);
                    else
                        inventory.remove(shoe.getId());

                    for (Account account : Account.getAccounts()) {
                        if (shoe.getQuantity() > 0) {
                            account.getCart().updateItemInList(shoe);
                            account.getWishlist().updateItemInList(shoe);
                        } else {
                            account.getCart().removeItemFromList(shoe.getId());
                            account.getWishlist().removeItemFromList(shoe.getId());
                        }
                    }
                }
            }

            return shoes;
        }

    }
}
