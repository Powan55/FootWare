package com.estore.api.estoreapi.controller;

import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDAO;

import com.estore.api.estoreapi.model.*;

/**
 * Handles the REST API requests for the Shoe resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty, Mathew Owusu Jr, Matthew Zhang, Laxmi Poudel
 */

@RestController
@RequestMapping("inventory")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDAO;

    public InventoryController(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * 
     * @param id used to find the associated shoe if it exists
     * @return the shoe with the associated id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Shoe> getShoe(@PathVariable int id) {
        LOG.info("GET /inventory/" + id);
        try {
            Shoe shoe = inventoryDAO.getShoe(id);
            if (shoe != null)
                return new ResponseEntity<Shoe>(shoe, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @return
     */
    @GetMapping("")
    public ResponseEntity<Shoe[]> getShoes() {
        LOG.info("GET /inventory");

        // Replace below with your implementation
        try {
            Shoe[] shoeList = inventoryDAO.getShoes();
            return new ResponseEntity<>(shoeList, HttpStatus.OK);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param shoe
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Shoe> createShoe(@RequestBody Shoe shoe) {
        LOG.info("POST /inventory " + shoe);

        try {
            Shoe newShoe = inventoryDAO.createShoe(shoe);
            if (newShoe != null) {
                return new ResponseEntity<Shoe>(newShoe, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Method for deleting a shoe from the inventory
     * 
     * @param shoe
     * 
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Shoe> deleteShoe(@PathVariable int id) {
        LOG.info("DELETE /inventory " + id);
        try {
            if (inventoryDAO.deleteShoe(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for searching for a shoe by name from the inventory
     * 
     * @param name
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Shoe[]> findShoes(@RequestParam String name) {
        LOG.info("GET /inventory/?name=" + name);

        try {
            Shoe[] shoe = inventoryDAO.findShoes(name);
            return new ResponseEntity<Shoe[]>(shoe, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param shoe
     * @return
     */
    @PutMapping("")
    public ResponseEntity<Shoe> updateShoe(@RequestBody Shoe shoe) {
        LOG.info(shoe + " being updated");
        try {
            Shoe newShoe = inventoryDAO.updateShoe(shoe);
            if (newShoe != null) {
                return new ResponseEntity<Shoe>(newShoe, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/updateShoes")
    public ResponseEntity<Shoe[]> updateShoes(@RequestBody Shoe[] shoes) {
        LOG.info(Arrays.toString(shoes) + "being updated");
        for (Shoe shoe : shoes) {
            if (shoe.getQuantity() <= 0)
                return new ResponseEntity<Shoe[]>(shoes, HttpStatus.BAD_REQUEST);
        }
        try {
            Shoe[] updatedShoes = inventoryDAO.decrementShoes(shoes);
            return new ResponseEntity<Shoe[]>(updatedShoes, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
