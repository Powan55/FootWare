package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.CouponDAO;

import com.estore.api.estoreapi.model.*;


/**
 * @author Mathew Owusu Jr
 */
@RestController
@RequestMapping("coupons")
public class CouponController {
    private static final Logger LOG = Logger.getLogger(CouponController.class.getName());
    private CouponDAO couponDAO;


    /**
     * 
     * @param couponDAO
     */
    public CouponController(CouponDAO couponDAO) {
        this.couponDAO = couponDAO;
    }
    

    /**
     * 
     * @param coupon
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        LOG.info("POST /coupon " + coupon);
        try {
            Coupon newCoupon = couponDAO.createCoupon(coupon);
            if (newCoupon != null) {
                return new ResponseEntity<Coupon>(newCoupon, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
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
    public ResponseEntity<Coupon[]> getCoupons() {
        LOG.info("GET /coupons");

        try {
            Coupon[] couponList = couponDAO.getCoupons();
            return new ResponseEntity<Coupon[]>(couponList, HttpStatus.OK);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String code){
        LOG.info("GET /coupons/" + code);
        try {
            Coupon coupon = couponDAO.getCoupon(code);
                if (coupon != null)
                    return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 
     * @param code
     * @return
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable String code) {
        LOG.info("DELETE /coupons/ " + code);
        try{
            if (couponDAO.deleteCoupon(code)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
