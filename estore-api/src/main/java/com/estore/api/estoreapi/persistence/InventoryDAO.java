 package com.estore.api.estoreapi.persistence;


import java.io.IOException;
import com.estore.api.estoreapi.model.Shoe;

/**
 * Defines the interface for Shoe object persistence
 * 
 * @author Mathew Owusu Jr, Matthew Zhang, Laxmi Poudel
 */
public interface InventoryDAO {

    /**
     * 
     * @param containsText finds shoe with associated name
     * @return
     * @throws IOException
     */
    Shoe[] findShoes(String containsText) throws IOException;

    /**
     * 
     * @return
     * @throws IOException
     */
    Shoe[] getShoes() throws IOException;

    /**
     * 
     * @param shoe
     * @return
     * @throws IOException
     */
    Shoe createShoe(Shoe shoe) throws IOException;

    /**
     * 
     * @param id finds shoe with associated id
     * @return shoe with associated id
     * @throws IOException
     */
    Shoe getShoe(int id) throws IOException;

    /**
     * 
     * @return
     * @throws IOException
     */
    Shoe updateShoe(Shoe shoe) throws IOException;
    
     /* Removed a shoe from the inventory
     * @param id to be removed
     * @throws IOException
     */
    Boolean deleteShoe(int id) throws IOException;   

    Shoe[] decrementShoes(Shoe[] shoes) throws IOException;
}
